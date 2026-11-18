
package br.com.mercearia_lino.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "item_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identifica a venda à qual o item pertence
    @ManyToOne
    @JoinColumn(name = "id_venda", nullable = false)
    private Vendas venda;

    // Identifica qual produto foi vendido
    @ManyToOne
    @JoinColumn(name = "fk_id_produto", nullable = false)
    private Produto produto;

    // Quantidade comprada
    @Column(nullable = false)
    private Integer quantidade;

    // Preço do produto no momento da venda
    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    // Valor total desse item
    @Column(nullable = false)
    private BigDecimal subtotal;
}
