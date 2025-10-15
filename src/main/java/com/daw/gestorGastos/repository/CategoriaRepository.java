package com.daw.gestorGastos.repository;

import com.daw.gestorGastos.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByTipo(String tipo);

    List<Categoria> findByTipoOrderByNombreAsc(String tipo);

    boolean existsByNombre(String nombre);
}