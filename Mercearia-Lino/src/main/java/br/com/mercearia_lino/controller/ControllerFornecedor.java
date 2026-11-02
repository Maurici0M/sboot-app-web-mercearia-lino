package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.domain.Fornecedor;
import br.com.mercearia_lino.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fornecedores")
public class ControllerFornecedor {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @PostMapping("/salvar")
    public String salvarFornecedor(@ModelAttribute Fornecedor fornecedor, RedirectAttributes redirectAttributes) {
        try {
            fornecedorRepository.save(fornecedor);

            redirectAttributes.addFlashAttribute("sucesso", "Fornecedor cadastrado com sucesso!");
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar fornecedor. Tente novamente.");
        }

        return "redirect:/produtos/cadastro";
    }

}
