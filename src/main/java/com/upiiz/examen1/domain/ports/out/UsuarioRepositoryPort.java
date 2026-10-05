package com.upiiz.examen1.domain.ports.out;

import com.upiiz.examen1.domain.model.Usuario;
import java.util.List;

public interface UsuarioRepositoryPort {
    Usuario guardar(Usuario usuario);
    boolean existePorCorreo(String correo);
    boolean existePorUsuario(String usuario);
    List<Usuario> buscarPorTexto(String texto);
}