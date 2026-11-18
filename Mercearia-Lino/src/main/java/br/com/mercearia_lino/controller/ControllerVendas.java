package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.domain.Vendas;
import br.com.mercearia_lino.enums.FormaPagamento;
import br.com.mercearia_lino.repository.ProdutoRepository;
import br.com.mercearia_lino.repository.VendasRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/vendas")
public class ControllerVendas {

    private final ProdutoRepository produtoRepository;
    @Autowired
    private VendasRepository vendasRepository;

    ControllerVendas(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    

    @GetMapping
    public String carregarPaginaVendas(Model model) {
        model.addAttribute("vendas", new Vendas());
        // Envia os valores do Enum FormaPagamento para o select do HTML funcionar
        model.addAttribute("EnumTipoPagamento", FormaPagamento.values());
        model.addAttribute("listaProdutos", produtoRepository.findAll());
        return "pages/vendas";
    }

    @PostMapping("/salvar-lote")
    @ResponseBody
    public ResponseEntity<String> salvarLote(@RequestBody List<Vendas> listaVendas) {
        try {
            // Garante que todas as vendas da lista tenham a data atual preenchida
            for (Vendas venda : listaVendas) {
                if (venda.getData() == null) {
                    venda.setData(java.time.LocalDate.now());
                }
            }
            
            // Salva a lista inteira de uma vez no banco de dados
            vendasRepository.saveAll(listaVendas);
            
            return ResponseEntity.ok("Vendas salvas com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar no banco");
        }
    }
    
}