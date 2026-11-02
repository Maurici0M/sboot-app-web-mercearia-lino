package br.com.mercearia_lino.domain;

import br.com.mercearia_lino.enums.FormaPagamento;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vendas {
    private int id;
    private List<Produto> listaProdutos;
    private double valorVenda;
    private int quantidadeProduto;
    private FormaPagamento formaPagamento;
    private Date data;
}
