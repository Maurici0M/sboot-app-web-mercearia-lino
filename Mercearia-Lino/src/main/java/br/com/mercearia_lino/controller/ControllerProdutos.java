package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.domain.Produto;
import br.com.mercearia_lino.enums.CategoriaProduto;
import br.com.mercearia_lino.enums.UnidadeMedida;
import br.com.mercearia_lino.repository.FornecedorRepository;
import br.com.mercearia_lino.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ControllerProdutos {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public String listaProdutos(
            Model model,
            @RequestParam(value = "nome", required = false) String nome) {

        if (nome != null) {
            List<Produto> listaProdutos = produtoRepository.findByNomeContainingIgnoreCase(nome);

            model.addAttribute("listaProdutos", listaProdutos);
            model.addAttribute("termoBusca", nome);

        } else {
            List<Produto> listaProdutos = produtoRepository.findAll();
            model.addAttribute("listaProdutos", listaProdutos);
        }

        return "pages/produtos";
    }

    @GetMapping("/relatorio")
    public ResponseEntity<byte[]> exportarRelatorioProdutos(
            @RequestParam(value = "nome", required = false) String nome) {

        List<Produto> produtos = (nome != null && !nome.isBlank())
                ? produtoRepository.findByNomeContainingIgnoreCase(nome)
                : produtoRepository.findAll();

        StringBuilder csv = new StringBuilder("\uFEFFID;Nome;Categoria;Unidade;Preco Unitario;Estoque;Fornecedor\n");

        produtos.forEach(p -> csv.append(p.getId()).append(";")
                .append(csvCampo(p.getNome())).append(";")
                .append(csvCampo(p.getCategoria())).append(";")
                .append(csvCampo(p.getUnidadeMedida())).append(";")
                .append(String.format("%.2f", p.getPrecoUnitario() != null ? p.getPrecoUnitario() : 0.0)).append(";")
                .append(csvCampo(p.getQuantidadeEstoque())).append(";")
                .append(csvCampo(p.getFornecedor() != null ? p.getFornecedor().getNome() : null)).append("\n"));

        return respostaCsv("relatorio-produtos.csv", csv.toString());
    }

    /*
     * estrutura padrao para extrairmos outros relatorios. equipe, nao modificar
     * aqui, somente no metodo de cima ::)
     */
    private String csvCampo(Object valor) {
        if (valor == null)
            return "";
        String texto = (valor instanceof Enum<?> e) ? e.name() : valor.toString();
        return "\"" + texto.replace("\"", "\"\"") + "\"";
    }

    private ResponseEntity<byte[]> respostaCsv(String nomeArquivo, String conteudo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "csv", StandardCharsets.UTF_8));
        headers.setContentDisposition(ContentDisposition.attachment().filename(nomeArquivo).build());
        return ResponseEntity.ok().headers(headers).body(conteudo.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("/cadastro")
    public String paginaCadastramento(Model model) {

        model.addAttribute("produto", new Produto());
        model.addAttribute("EnumCategoriaProduto", CategoriaProduto.values());
        model.addAttribute("EnumUnidadeMedida", UnidadeMedida.values());
        model.addAttribute("listaFornecedores", fornecedorRepository.findAll());

        return "pages/cadastro-produto";
    }

    @PostMapping("/salvar")
    public String salvarProduto(
            @ModelAttribute Produto produto,
            RedirectAttributes redirectAttributes) {

        try {
            // Validação do preço
            if (produto.getPrecoUnitario() <= 0.01) {
                throw new IllegalArgumentException(
                        "O preço do produto deve ser maior que R$ 0,01.");
            }

            // Validação da quantidade
            if (produto.getQuantidadeEstoque() <= 1) {
                throw new IllegalArgumentException(
                        "A quantidade em estoque deve ser maior que 1.");
            }

            // Validação do nome
            if (produto.getNome() == null || produto.getNome().isBlank()) {
                throw new IllegalArgumentException(
                        "O nome do produto não pode ficar em branco.");
            }

            produtoRepository.save(produto);

            redirectAttributes.addFlashAttribute(
                    "sucesso", "Produto cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "erro", e.getMessage());

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "erro", "Erro ao cadastrar produto. Tente novamente.");
        }

        return "redirect:/produtos/cadastro";
    }

    @PostMapping("/excluir/{id}")
    public String excluirProduto(
            @PathVariable int id,
            RedirectAttributes redirectAttributes) {

        try {
            if (!produtoRepository.existsById(id)) {
                throw new IllegalArgumentException(
                        "Produto não encontrado!");
            }

            produtoRepository.deleteById(id);

            redirectAttributes.addFlashAttribute(
                    "sucesso", "Produto excluído com sucesso!");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "erro", e.getMessage());

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível excluir o produto.");
        }

        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String paginaEdicao(
            @PathVariable int id,
            Model model,
            RedirectAttributes redirectAttributes) {

        Produto produto = produtoRepository.findById(id).orElse(null);

        if (produto == null) {
            redirectAttributes.addFlashAttribute(
                    "erro", "Produto não encontrado!");
            return "redirect:/produtos";
        }

        model.addAttribute("produto", produto);
        model.addAttribute("EnumCategoriaProduto", CategoriaProduto.values());
        model.addAttribute("EnumUnidadeMedida", UnidadeMedida.values());
        model.addAttribute("listaFornecedores", fornecedorRepository.findAll());

        return "pages/cadastro-produto";
    }

    // NOVO: SALVAR AS ALTERAÇÕES DO PRODUTO
    @PostMapping("/editar/{id}")
    public String editarProduto(
            @PathVariable int id,
            @ModelAttribute Produto dadosAtualizados,
            RedirectAttributes redirectAttributes) {

        try {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto não encontrado!"));

            // Validação do nome
            if (dadosAtualizados.getNome() == null ||
                    dadosAtualizados.getNome().isBlank()) {
                throw new IllegalArgumentException(
                        "O nome do produto não pode ficar em branco.");
            }

            // Validação do preço
            if (dadosAtualizados.getPrecoUnitario() <= 0.01) {
                throw new IllegalArgumentException(
                        "O preço do produto deve ser maior que R$ 0,01.");
            }

            // Validação da quantidade
            if (dadosAtualizados.getQuantidadeEstoque() <= 1) {
                throw new IllegalArgumentException(
                        "A quantidade em estoque deve ser maior que 1.");
            }

            // Atualiza somente os dados permitidos
            produto.setNome(dadosAtualizados.getNome());
            produto.setCategoria(dadosAtualizados.getCategoria());
            produto.setFornecedor(dadosAtualizados.getFornecedor());
            produto.setPrecoUnitario(dadosAtualizados.getPrecoUnitario());
            produto.setQuantidadeEstoque(dadosAtualizados.getQuantidadeEstoque());
            produto.setUnidadeMedida(dadosAtualizados.getUnidadeMedida());

            // O ID original permanece inalterado
            produtoRepository.save(produto);

            redirectAttributes.addFlashAttribute(
                    "sucesso", "Produto atualizado com sucesso!");

            return "redirect:/produtos";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "erro", e.getMessage());

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "erro", "Não foi possível atualizar o produto.");
        }

        return "redirect:/produtos/editar/" + id;
    }
}
