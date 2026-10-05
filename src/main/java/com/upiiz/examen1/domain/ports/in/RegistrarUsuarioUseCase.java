package com.upiiz.examen1.domain.ports.in;

import com.upiiz.examen1.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {
    Usuario registrar(Usuario usuario);
}