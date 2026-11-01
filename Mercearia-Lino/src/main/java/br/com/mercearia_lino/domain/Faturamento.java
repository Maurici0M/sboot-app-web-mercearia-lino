package br.com.mercearia_lino.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Faturamento {
    private int id;
    private List<Vendas> vendas;
}
