package br.com.mercearia_lino.domain;

import br.com.mercearia_lino.enums.CategoriaProduto;
import br.com.mercearia_lino.enums.UnidadeMedida;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "fk_id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "precounitario", nullable = false)
    private Double precoUnitario;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoriaproduto", nullable = false, columnDefinition = "TEXT")
    private CategoriaProduto categoria;

    @Column(name = "quantidadeestoque", nullable = false)
    private Integer quantidadeEstoque;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidademedida", nullable = false, columnDefinition = "TEXT")
    private UnidadeMedida unidadeMedida;
}