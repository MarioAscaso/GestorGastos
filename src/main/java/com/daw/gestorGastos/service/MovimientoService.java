package com.daw.gestorGastos.service;

import com.daw.gestorGastos.model.dto.MovimientoDTO;
import com.daw.gestorGastos.model.entity.Movimiento;
import com.daw.gestorGastos.model.entity.Usuario;
import com.daw.gestorGastos.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;  // <-- AÑADE ESTE IMPORT
import java.util.stream.Collectors;

@Service
@Transactional
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    public List<MovimientoDTO> obtenerTodosMovimientosDTO() {
        Usuario usuario = usuarioService.obtenerUsuarioPorDefecto();
        return movimientoRepository.findByUsuarioOrderByFechaDesc(usuario)
                .stream()
                .map(this::convertirToDTO)
                .collect(Collectors.toList());
    }

    public List<MovimientoDTO> obtenerMovimientosDelMes() {
        Usuario usuario = usuarioService.obtenerUsuarioPorDefecto();
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        return movimientoRepository.findByUsuarioAndFechaBetweenOrderByFechaDesc(usuario, inicioMes, finMes)
                .stream()
                .map(this::convertirToDTO)
                .collect(Collectors.toList());
    }

    public Movimiento guardar(Movimiento movimiento) {
        if (movimiento.getUsuario() == null) {
            movimiento.setUsuario(usuarioService.obtenerUsuarioPorDefecto());
        }
        return movimientoRepository.save(movimiento);
    }

    public Optional<Movimiento> obtenerPorId(Long id) {
        return movimientoRepository.findById(id);
    }

    public void eliminar(Long id) {
        movimientoRepository.deleteById(id);
    }

    public Double obtenerTotalIngresosDelMes() {
        Usuario usuario = usuarioService.obtenerUsuarioPorDefecto();
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        List<Object[]> resultados = movimientoRepository.getResumenPorTipo(usuario, inicioMes, finMes);

        return resultados.stream()
                .filter(r -> "INGRESO".equals(r[0]))
                .map(r -> (Double) r[1])
                .findFirst()
                .orElse(0.0);
    }

    public Double obtenerTotalGastosDelMes() {
        Usuario usuario = usuarioService.obtenerUsuarioPorDefecto();
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        List<Object[]> resultados = movimientoRepository.getResumenPorTipo(usuario, inicioMes, finMes);

        return resultados.stream()
                .filter(r -> "GASTO".equals(r[0]))
                .map(r -> (Double) r[1])
                .findFirst()
                .orElse(0.0);
    }

    private MovimientoDTO convertirToDTO(Movimiento movimiento) {
        return new MovimientoDTO(
                movimiento.getId(),
                movimiento.getDescripcion(),
                movimiento.getCantidad(),
                movimiento.getFecha(),
                movimiento.getCategoria().getNombre(),
                movimiento.getCategoria().getTipo(),
                movimiento.getCategoria().getColor()
        );
    }
}