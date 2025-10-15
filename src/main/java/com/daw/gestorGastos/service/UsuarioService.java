package com.daw.gestorGastos.service;

import com.daw.gestorGastos.model.entity.Usuario;
import com.daw.gestorGastos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuarioPorDefecto() {
        // Para single-user, creamos un usuario por defecto
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail("usuario@gestor.com");
        if (usuarioExistente.isPresent()) {
            System.out.println("Usuario existente encontrado: " + usuarioExistente.get().getEmail());
            return usuarioExistente.get();
        }

        Usuario usuario = new Usuario("Usuario Principal", "usuario@gestor.com", "password123");
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        System.out.println("Nuevo usuario creado: " + usuarioGuardado.getEmail());
        return usuarioGuardado;
    }

    public Usuario obtenerUsuarioPorDefecto() {
        return usuarioRepository.findByEmail("usuario@gestor.com")
                .orElseGet(this::crearUsuarioPorDefecto);
    }
}