package br.com.mercearia_lino.domain;

import br.com.mercearia_lino.enums.FormaPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "venda")
public class Vendas {

    // Identificador único da venda
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Data em que a venda foi realizada
    @Column(name = "data", nullable = false)
    private LocalDate data;

    // Forma de pagamento
    @Enumerated(EnumType.STRING)
    @Column(name = "formapagamento", nullable = false)
    private FormaPagamento formaPagamento;

    // Valor total da compra
    @Column(name = "total", nullable = false,
            precision = 10, scale = 2)
    private BigDecimal total;

    // Uma venda pode conter vários produtos
    @OneToMany(
        mappedBy = "venda",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ItemVenda> itens = new ArrayList<>();

    // Adiciona um produto à venda
    public void adicionarItem(ItemVenda item) {
        itens.add(item);
        item.setVenda(this);
    }
}
