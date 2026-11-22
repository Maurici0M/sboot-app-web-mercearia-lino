package br.com.mercearia_lino.repository;

import br.com.mercearia_lino.domain.Vendas;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface VendasRepository extends JpaRepository<Vendas, Long> {
    @Query("SELECT SUM(v.total) FROM Vendas v WHERE v.data = CURRENT_DATE")
    Double totalVendasHoje();

    @Query("SELECT COALESCE(SUM(v.total), 0.0) FROM Vendas v WHERE YEAR(v.data) = YEAR(CURRENT_DATE) AND MONTH(v.data) = MONTH(CURRENT_DATE)")
    Double totalVendasMes();

   @Query("SELECT COALESCE(SUM(v.total), 0.0) FROM Vendas v WHERE v.data >= :dataInicial AND v.data <= CURRENT_DATE")
    Double totalVendasSemana(@Param("dataInicial") LocalDate dataInicial);

}