package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.domain.ItemVenda;
import br.com.mercearia_lino.domain.Produto;
import br.com.mercearia_lino.domain.Vendas;
import br.com.mercearia_lino.enums.FormaPagamento;
import br.com.mercearia_lino.repository.ProdutoRepository;
import br.com.mercearia_lino.repository.VendasRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vendas")
public class ControllerVendas {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendasRepository vendasRepository;

    // ABRIR A TELA DE VENDAS
    @GetMapping
    public String carregarPaginaVendas(Model model) {

        model.addAttribute("vendas", new Vendas());
        model.addAttribute(
            "EnumTipoPagamento", FormaPagamento.values()
        );
        model.addAttribute(
            "listaProdutos", produtoRepository.findAll()
        );

        return "pages/vendas";
    }

    // DADOS RECEBIDOS DO FORMULÁRIO
    public record ItemPedido(
        Integer produtoId,
        Integer quantidade
    ) {}

    public record PedidoVenda(
        FormaPagamento formaPagamento,
        List<ItemPedido> itens
    ) {}

    // SALVAR UMA VENDA COM VÁRIOS ITENS
    @PostMapping("/concluir")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> salvarVenda(
            @RequestBody PedidoVenda pedido) {

        // Verifica se a venda possui produtos
        if (pedido == null ||
            pedido.itens() == null ||
            pedido.itens().isEmpty()) {

            throw new IllegalArgumentException(
                "Adicione pelo menos um produto à venda."
            );
        }

        // Verifica a forma de pagamento
        if (pedido.formaPagamento() == null) {
            throw new IllegalArgumentException(
                "Selecione a forma de pagamento."
            );
        }

        // Agrupa quantidades do mesmo produto
        Map<Integer, Integer> quantidades =
            new LinkedHashMap<>();

        for (ItemPedido item : pedido.itens()) {

            if (item == null ||
                item.produtoId() == null ||
                item.quantidade() == null ||
                item.quantidade() <= 0) {

                throw new IllegalArgumentException(
                    "Produto ou quantidade inválida."
                );
            }

            quantidades.merge(
                item.produtoId(),
                item.quantidade(),
                Integer::sum
            );
        }

        // Cria uma única venda
        Vendas venda = new Vendas();

        venda.setData(LocalDate.now());
        venda.setFormaPagamento(pedido.formaPagamento());

        BigDecimal total = BigDecimal.ZERO;

        // Percorre os produtos da compra
        for (Map.Entry<Integer, Integer> entrada
                : quantidades.entrySet()) {

            Integer idProduto = entrada.getKey();
            Integer quantidade = entrada.getValue();

            // Busca o produto no banco
            Produto produto = produtoRepository
                .findById(idProduto)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Produto não encontrado!"
                    )
                );

            // Verifica o estoque disponível
            if (produto.getQuantidadeEstoque() == null ||
                quantidade > produto.getQuantidadeEstoque()) {

                throw new IllegalArgumentException(
                    "Estoque insuficiente para: " +
                    produto.getNome()
                );
            }

            if (produto.getPrecoUnitario() == null ||
                !Double.isFinite(produto.getPrecoUnitario()) ||
                produto.getPrecoUnitario() <= 0) {

                throw new IllegalArgumentException(
                    "Preço inválido para: " +
                    produto.getNome()
                );
            }

            // Calcula o valor do item
            BigDecimal preco = BigDecimal
                .valueOf(produto.getPrecoUnitario())
                .setScale(2, RoundingMode.HALF_UP);

            BigDecimal subtotal = preco.multiply(
                BigDecimal.valueOf(quantidade)
            );

            // Cria o item da venda
            ItemVenda itemVenda = new ItemVenda();

            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(quantidade);
            itemVenda.setPrecoUnitario(preco);
            itemVenda.setSubtotal(subtotal);

            // Associa o item à venda principal
            venda.adicionarItem(itemVenda);

            total = total.add(subtotal);

            // Atualiza a quantidade do estoque
            produto.setQuantidadeEstoque(
                produto.getQuantidadeEstoque() - quantidade
            );

            produtoRepository.save(produto);
        }

        // Define o total da compra
        venda.setTotal(total);

        // Salva a venda com todos os seus itens
        vendasRepository.save(venda);

        return ResponseEntity.ok(
            "Venda registrada com sucesso!" 
        );
    }

    // TRATAR ERROS DE VALIDAÇÃO
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> tratarErro(
            IllegalArgumentException e) {

        return ResponseEntity
            .badRequest()
            .body(e.getMessage());
    }
}
