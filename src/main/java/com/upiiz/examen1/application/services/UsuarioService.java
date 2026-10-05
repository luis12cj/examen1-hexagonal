package com.upiiz.examen1.application.services;

import com.upiiz.examen1.domain.model.Usuario;
import com.upiiz.examen1.domain.ports.in.BuscarUsuariosUseCase;
import com.upiiz.examen1.domain.ports.in.RegistrarUsuarioUseCase;
import com.upiiz.examen1.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService implements RegistrarUsuarioUseCase, BuscarUsuariosUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public UsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        // Regla 1: Campos obligatorios
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty() ||
                usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno().trim().isEmpty() ||
                usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno().trim().isEmpty() ||
                usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty() ||
                usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty() ||
                usuario.getPassword() == null || usuario.getPassword().trim().isEmpty() ||
                usuario.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        // Regla 2: Correo formato válido
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!usuario.getCorreo().matches(emailRegex)) {
            throw new IllegalArgumentException("El correo debe tener un formato válido.");
        }

        // Regla 3: Correo único
        if (usuarioRepositoryPort.existePorCorreo(usuario.getCorreo().trim())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Regla 4: Nombre de usuario único
        if (usuarioRepositoryPort.existePorUsuario(usuario.getUsuario().trim())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado.");
        }

        // Regla 5: Usuario mínimo 5 caracteres
        if (usuario.getUsuario().trim().length() < 5) {
            throw new IllegalArgumentException("El usuario deberá tener al menos 5 caracteres.");
        }

        // Regla 6: Contraseña mínimo 8 caracteres
        if (usuario.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña deberá tener al menos 8 caracteres.");
        }

        // Regla 7: Fecha nacimiento válida (no en el futuro)
        if (usuario.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento deberá ser válida.");
        }

        return usuarioRepositoryPort.guardar(usuario);
    }

    @Override
    public List<Usuario> buscarPorTexto(String texto) {
        // Restricción: Validar al menos 3 caracteres en backend
        if (texto == null || texto.trim().length() < 3) {
            throw new IllegalArgumentException("La búsqueda debe contener al menos 3 caracteres.");
        }
        return usuarioRepositoryPort.buscarPorTexto(texto.trim());
    }
}