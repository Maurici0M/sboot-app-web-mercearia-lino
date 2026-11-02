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
    private int id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String nome;

    // cardinalidade das tabelas
    @ManyToOne
    @JoinColumn(name = "fk_id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "precounitario", nullable = false)
    private double precoUnitario;

    @Enumerated(EnumType.STRING) //transforma a enum que temos em string para salvar no db
    @Column(name = "categoriaproduto", nullable = false, columnDefinition = "TEXT")
    private CategoriaProduto categoria;

    @Column(name = "quantidadeestoque", nullable = false)
    private int quantidadeEstoque;

    @Enumerated(EnumType.STRING) //transforma a enum que temos em string para salvar no db
    @Column(name = "unidademedida", nullable = false, columnDefinition = "TEXT")
    private UnidadeMedida unidadeMedida;

    
    public Produto(
            String nome,
            Fornecedor fornecedor,
            double precoUnitario,
            CategoriaProduto categoriaProduto,
            int quantidadeEstoque,
            UnidadeMedida unidadeMedida
    ) {
        this.nome = nome;
        this.fornecedor = fornecedor;
        this.precoUnitario = precoUnitario;
        this.categoria = categoriaProduto;
        this.quantidadeEstoque = quantidadeEstoque;
        this.unidadeMedida = unidadeMedida;
    }
}