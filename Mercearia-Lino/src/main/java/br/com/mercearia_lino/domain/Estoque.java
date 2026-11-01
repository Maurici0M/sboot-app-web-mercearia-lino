package br.com.mercearia_lino.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Estoque {
    private List<Produtos> listaProdutos;

}
