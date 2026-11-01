package br.com.mercearia_lino.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fornecedor {
    private int id;
    private String nome;
    private String telefone;
    private String site;
}
