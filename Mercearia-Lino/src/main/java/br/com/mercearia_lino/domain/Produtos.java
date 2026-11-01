package br.com.mercearia_lino.domain;

import br.com.mercearia_lino.enums.CategoriaProduto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Produtos {
    private int id;
    private String nome;
    private Fornecedor fornecedor;
    private double precoUnitario;
    private CategoriaProduto categoria;
    private int quantidadeEstoque;

}
