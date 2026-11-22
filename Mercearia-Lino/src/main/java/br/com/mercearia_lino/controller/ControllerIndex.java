package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.repository.ProdutoRepository;
import br.com.mercearia_lino.repository.VendasRepository;

import java.time.LocalDate;

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

    @Autowired
    private VendasRepository vendasRepository;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("quantidadeEstoque", produtoRepository.somaTotalEstoque());

        model.addAttribute("valorVendasHoje", vendasRepository.totalVendasHoje());

        model.addAttribute("valorVendasMes", vendasRepository.totalVendasMes());

        model.addAttribute("valorVendasSemana", vendasRepository.totalVendasSemana(LocalDate.now().minusDays(7)));
        
        return "index";
    }

}
