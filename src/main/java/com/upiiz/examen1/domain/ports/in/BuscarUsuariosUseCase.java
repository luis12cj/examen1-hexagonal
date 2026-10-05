package com.upiiz.examen1.domain.ports.in;

import com.upiiz.examen1.domain.model.Usuario;
import java.util.List;

public interface BuscarUsuariosUseCase {
    List<Usuario> buscarPorTexto(String texto);
}