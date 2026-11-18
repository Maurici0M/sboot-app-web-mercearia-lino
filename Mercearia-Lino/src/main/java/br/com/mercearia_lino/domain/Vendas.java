package br.com.mercearia_lino.domain;

import br.com.mercearia_lino.enums.FormaPagamento;
import jakarta.persistence.*;
import java.time.LocalDate; 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venda")
public class Vendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fk_id_produto", nullable = false)
    private Produto produto;
    @Column(nullable = false)
    private Integer quantidade;
     @Column(nullable = false)
    private Double valorVenda;
    @Enumerated(EnumType.STRING) //Salva o Enum como texto no banco
    @Column (name = "formapagamento", nullable = false)
    private FormaPagamento formaPagamento;
    @Column (name = "data")
    private LocalDate data;
}
