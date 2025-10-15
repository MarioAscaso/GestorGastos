package com.daw.gestorGastos.service;

import com.daw.gestorGastos.model.entity.Categoria;
import com.daw.gestorGastos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> obtenerPorTipo(String tipo) {
        return categoriaRepository.findByTipoOrderByNombreAsc(tipo);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> obtenerPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    @PostConstruct
    public void inicializarCategoriasPorDefecto() {
        // Verificar si ya existen categorías
        if (categoriaRepository.count() == 0) {
            System.out.println("=== INICIALIZANDO CATEGORÍAS POR DEFECTO ===");

            List<Categoria> categorias = Arrays.asList(
                    new Categoria("Nómina", "INGRESO", "#28a745"),
                    new Categoria("Freelance", "INGRESO", "#20c997"),
                    new Categoria("Inversiones", "INGRESO", "#17a2b8"),
                    new Categoria("Alquiler", "GASTO", "#dc3545"),
                    new Categoria("Comida", "GASTO", "#fd7e14"),
                    new Categoria("Transporte", "GASTO", "#6f42c1"),
                    new Categoria("Ocio", "GASTO", "#e83e8c"),
                    new Categoria("Salud", "GASTO", "#6c757d")
            );

            categoriaRepository.saveAll(categorias);
            System.out.println("=== " + categorias.size() + " CATEGORÍAS CREADAS ===");
        } else {
            System.out.println("=== YA EXISTEN CATEGORÍAS EN LA BD ===");
        }
    }
}