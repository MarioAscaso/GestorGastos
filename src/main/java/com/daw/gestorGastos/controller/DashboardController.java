package com.daw.gestorGastos.controller;

import com.daw.gestorGastos.model.dto.MovimientoDTO;
import com.daw.gestorGastos.model.entity.Categoria;
import com.daw.gestorGastos.service.CategoriaService;
import com.daw.gestorGastos.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class DashboardController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String dashboard(Model model) {
        try {
            List<MovimientoDTO> movimientos = movimientoService.obtenerMovimientosDelMes();
            Double totalIngresos = movimientoService.obtenerTotalIngresosDelMes();
            Double totalGastos = movimientoService.obtenerTotalGastosDelMes();
            Double balance = totalIngresos - totalGastos;
            List<Categoria> categorias = categoriaService.obtenerTodas();

            System.out.println("=== DATOS DEL DASHBOARD ===");
            System.out.println("Movimientos: " + movimientos.size());
            System.out.println("Categorías: " + categorias.size());
            System.out.println("Total Ingresos: " + totalIngresos);
            System.out.println("Total Gastos: " + totalGastos);

            model.addAttribute("movimientos", movimientos);
            model.addAttribute("totalIngresos", totalIngresos);
            model.addAttribute("totalGastos", totalGastos);
            model.addAttribute("balance", balance);
            model.addAttribute("categorias", categorias);

            return "dashboard";
        } catch (Exception e) {
            System.err.println("ERROR en dashboard: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar el dashboard: " + e.getMessage());
            return "error";
        }
    }
}