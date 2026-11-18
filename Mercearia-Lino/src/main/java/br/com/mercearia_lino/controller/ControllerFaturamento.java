package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.domain.Vendas;
import br.com.mercearia_lino.enums.FormaPagamento;
import br.com.mercearia_lino.repository.VendasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/faturamento")
public class ControllerFaturamento {

    @Autowired
    private VendasRepository vendasRepository;

    // LISTAR VENDAS
    @GetMapping
    public String listarFaturamento(Model model) {

        model.addAttribute("listaVendas",
            vendasRepository.findAll(
                Sort.by(Sort.Direction.DESC, "id")
            )
        );

        model.addAttribute(
            "formasPagamento",
            FormaPagamento.values()
        );

        return "pages/faturamento";
    }

    // EDITAR FORMA DE PAGAMENTO
    @PostMapping("/editar/{id}")
    public String editarVenda(
            @PathVariable Long id,
            @RequestParam FormaPagamento formaPagamento,
            RedirectAttributes redirect) {

        Vendas venda = vendasRepository.findById(id)
            .orElse(null);

        if (venda == null) {
            redirect.addFlashAttribute(
                "erro",
                "Venda não encontrada!"
            );

            return "redirect:/faturamento";
        }

        venda.setFormaPagamento(formaPagamento);

        vendasRepository.save(venda);

        redirect.addFlashAttribute(
            "sucesso",
            "Venda atualizada com sucesso!"
        );

        return "redirect:/faturamento";
    }
}
