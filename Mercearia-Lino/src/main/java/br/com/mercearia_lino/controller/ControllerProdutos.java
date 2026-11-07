package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.domain.Produto;
import br.com.mercearia_lino.enums.CategoriaProduto;
import br.com.mercearia_lino.enums.UnidadeMedida;
import br.com.mercearia_lino.repository.FornecedorRepository;
import br.com.mercearia_lino.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ControllerProdutos {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public String listaProdutos(Model model, @RequestParam(value = "nome", required = false) String nome) {

        if (nome != null) {
            List<Produto> listaProdutos = produtoRepository.findByNomeContainingIgnoreCase(nome);

            model.addAttribute("listaProdutos", listaProdutos);

            model.addAttribute("termoBusca", nome);

        } else {
            List<Produto> listaProdutos  = produtoRepository.findAll();

            model.addAttribute("listaProdutos", listaProdutos);
        }

        return "pages/produtos";
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
    public String salvarProduto(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
        try {
            produtoRepository.save(produto);

            redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar produto. Tente novamente.");
        }

        return "redirect:/produtos/cadastro";
    }
    
}
