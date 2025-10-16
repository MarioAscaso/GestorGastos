package com.daw.gestorGastos.controller;

import com.daw.gestorGastos.model.entity.Movimiento;
import com.daw.gestorGastos.model.entity.Categoria;
import com.daw.gestorGastos.model.entity.Usuario;
import com.daw.gestorGastos.service.MovimientoService;
import com.daw.gestorGastos.service.CategoriaService;
import com.daw.gestorGastos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoMovimiento(Model model) {
        model.addAttribute("movimiento", new Movimiento());
        model.addAttribute("categorias", categoriaService.obtenerTodas());
        return "movimientos/form";
    }

    @PostMapping("/guardar")
    public String guardarMovimiento(@RequestParam("descripcion") String descripcion,
                                    @RequestParam("cantidad") Double cantidad,
                                    @RequestParam("categoriaId") Long categoriaId,
                                    RedirectAttributes redirectAttributes) {
        try {
            System.out.println("=== INTENTANDO GUARDAR MOVIMIENTO ===");
            System.out.println("Descripción: " + descripcion);
            System.out.println("Cantidad: " + cantidad);
            System.out.println("Categoría ID: " + categoriaId);

            if (descripcion == null || descripcion.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "La descripción es obligatoria");
                return "redirect:/";
            }

            if (cantidad == null || cantidad <= 0) {
                redirectAttributes.addFlashAttribute("error", "La cantidad debe ser mayor a 0");
                return "redirect:/";
            }

            Movimiento movimiento = new Movimiento();
            movimiento.setDescripcion(descripcion.trim());
            movimiento.setCantidad(cantidad);
            movimiento.setFecha(java.time.LocalDate.now());

            Usuario usuario = usuarioService.obtenerUsuarioPorDefecto();
            movimiento.setUsuario(usuario);
            System.out.println("Usuario asignado: " + usuario.getEmail());

            Optional<Categoria> categoriaOpt = categoriaService.obtenerPorId(categoriaId);
            if (categoriaOpt.isPresent()) {
                Categoria categoria = categoriaOpt.get();
                movimiento.setCategoria(categoria);
                System.out.println("Categoría asignada: " + categoria.getNombre());

                Movimiento movimientoGuardado = movimientoService.guardar(movimiento);
                System.out.println("Movimiento guardado con ID: " + movimientoGuardado.getId());

                redirectAttributes.addFlashAttribute("success",
                        "Movimiento '" + descripcion + "' guardado correctamente");
            } else {
                System.out.println("Categoría no encontrada para ID: " + categoriaId);
                redirectAttributes.addFlashAttribute("error", "Categoría no encontrada");
            }

        } catch (Exception e) {
            System.err.println("ERROR al guardar movimiento: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Error al guardar el movimiento: " + e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarMovimiento(@PathVariable Long id, Model model) {
        Optional<Movimiento> movimientoOpt = movimientoService.obtenerPorId(id);
        if (movimientoOpt.isPresent()) {
            model.addAttribute("movimiento", movimientoOpt.get());
            model.addAttribute("categorias", categoriaService.obtenerTodas());
            return "movimientos/form";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarMovimiento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            movimientoService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Movimiento eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el movimiento");
        }
        return "redirect:/";
    }
}