package br.com.mercearia_lino.domain;

import br.com.mercearia_lino.enums.FormaPagamento;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vendas {
    private int id;
    private List<Produtos> listaProdutos;
    private double valorVenda;
    private FormaPagamento formaPagamento;
}
