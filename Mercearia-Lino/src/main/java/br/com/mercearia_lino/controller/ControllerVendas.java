package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.domain.Vendas;
import br.com.mercearia_lino.enums.FormaPagamento;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendas")
public class ControllerVendas {
    @GetMapping
    public String home(Model model) {

        model.addAttribute("vendas", new Vendas());

        model.addAttribute("EnumTipoPagamento", FormaPagamento.values());

        return "pages/vendas";
    }

}
