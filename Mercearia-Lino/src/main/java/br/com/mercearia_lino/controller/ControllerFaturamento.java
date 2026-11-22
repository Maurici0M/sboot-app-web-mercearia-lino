package br.com.mercearia_lino.controller;

import br.com.mercearia_lino.domain.Vendas;
import br.com.mercearia_lino.enums.FormaPagamento;
import br.com.mercearia_lino.repository.VendasRepository;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/faturamento")
public class ControllerFaturamento {

        @Autowired
        private VendasRepository vendasRepository;

        @GetMapping
        public String listarFaturamento(Model model) {

                model.addAttribute("listaVendas",
                                vendasRepository.findAll(
                                                Sort.by(Sort.Direction.DESC, "id")));

                model.addAttribute(
                                "formasPagamento",
                                FormaPagamento.values());

                return "pages/faturamento";
        }

        @GetMapping("/relatorio")
        public ResponseEntity<byte[]> exportarRelatorioFaturamento(
                        @RequestParam(value = "formaPagamento", required = false) FormaPagamento formaPagamento) {

                List<Vendas> vendas = vendasRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                                .filter(v -> formaPagamento == null || formaPagamento.equals(v.getFormaPagamento()))
                                .toList();

                BigDecimal somaTotal = vendas.stream()
                                .map(v -> v.getTotal() != null ? v.getTotal() : BigDecimal.ZERO)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                StringBuilder csv = new StringBuilder("\uFEFFID Venda;Data;Forma de Pagamento;Total\n");

                vendas.forEach(v -> csv.append(v.getId()).append(";")
                                .append(csvCampo(v.getData())).append(";")
                                .append(csvCampo(v.getFormaPagamento())).append(";")
                                .append(String.format("%.2f", v.getTotal() != null ? v.getTotal() : BigDecimal.ZERO))
                                .append("\n"));

                csv.append("\n;;TOTAL FATURADO;").append(String.format("%.2f", somaTotal)).append("\n");

                return respostaCsv("relatorio-faturamento.csv", csv.toString());
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
                                        "Venda não encontrada!");

                        return "redirect:/faturamento";
                }

                venda.setFormaPagamento(formaPagamento);

                vendasRepository.save(venda);

                redirect.addFlashAttribute(
                                "sucesso",
                                "Venda atualizada com sucesso!");

                return "redirect:/faturamento";
        }
}
