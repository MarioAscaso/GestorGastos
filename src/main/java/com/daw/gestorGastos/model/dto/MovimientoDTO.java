package com.daw.gestorGastos.model.dto;

import java.time.LocalDate;

public class MovimientoDTO {
    private Long id;
    private String descripcion;
    private Double cantidad;
    private LocalDate fecha;
    private String categoriaNombre;
    private String categoriaTipo;
    private String categoriaColor;

    // Constructores
    public MovimientoDTO() {
    }

    public MovimientoDTO(Long id, String descripcion, Double cantidad, LocalDate fecha,
                         String categoriaNombre, String categoriaTipo, String categoriaColor) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.categoriaNombre = categoriaNombre;
        this.categoriaTipo = categoriaTipo;
        this.categoriaColor = categoriaColor;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getCategoriaTipo() {
        return categoriaTipo;
    }

    public void setCategoriaTipo(String categoriaTipo) {
        this.categoriaTipo = categoriaTipo;
    }

    public String getCategoriaColor() {
        return categoriaColor;
    }

    public void setCategoriaColor(String categoriaColor) {
        this.categoriaColor = categoriaColor;
    }
}