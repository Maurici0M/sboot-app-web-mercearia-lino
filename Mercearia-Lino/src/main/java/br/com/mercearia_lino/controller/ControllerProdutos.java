package br.com.mercearia_lino.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produtos")
public class ControllerProdutos {
    
    @GetMapping("/cadastro")
    public String teste() {
        
        return "pages/cadastro-produto";
    }
    
}
