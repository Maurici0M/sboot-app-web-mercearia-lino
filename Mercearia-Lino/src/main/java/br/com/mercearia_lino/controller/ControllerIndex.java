package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ControllerIndex {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("quantidadeEstoque", produtoRepository.somaTotalEstoque());

        return "index";
    }

}
