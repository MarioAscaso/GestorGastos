package com.daw.gestorGastos.repository;

import com.daw.gestorGastos.model.entity.Movimiento;
import com.daw.gestorGastos.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByUsuarioOrderByFechaDesc(Usuario usuario);

    List<Movimiento> findByUsuarioAndFechaBetweenOrderByFechaDesc(Usuario usuario, LocalDate inicio, LocalDate fin);

    @Query("SELECT m FROM Movimiento m WHERE m.usuario = :usuario AND YEAR(m.fecha) = :year AND MONTH(m.fecha) = :month ORDER BY m.fecha DESC")
    List<Movimiento> findByUsuarioAndMonthAndYear(@Param("usuario") Usuario usuario,
                                                  @Param("month") int month,
                                                  @Param("year") int year);

    @Query("SELECT m.categoria.tipo, SUM(m.cantidad) FROM Movimiento m WHERE m.usuario = :usuario AND m.fecha BETWEEN :start AND :end GROUP BY m.categoria.tipo")
    List<Object[]> getResumenPorTipo(@Param("usuario") Usuario usuario,
                                     @Param("start") LocalDate start,
                                     @Param("end") LocalDate end);
}