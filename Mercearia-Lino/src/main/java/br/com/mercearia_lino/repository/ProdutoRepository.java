package br.com.mercearia_lino.repository;

import br.com.mercearia_lino.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Query("SELECT SUM(p.quantidadeEstoque) FROM Produto p")
    Integer somaTotalEstoque();
}
