package com.upiiz.examen1.adapters.in.web;

import com.upiiz.examen1.adapters.in.web.dto.RegistroUsuarioDTO;
import com.upiiz.examen1.adapters.in.web.dto.UsuarioResponseDTO;
import com.upiiz.examen1.domain.model.Usuario;
import com.upiiz.examen1.domain.ports.in.BuscarUsuariosUseCase;
import com.upiiz.examen1.domain.ports.in.RegistrarUsuarioUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final BuscarUsuariosUseCase buscarUsuariosUseCase;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                             BuscarUsuariosUseCase buscarUsuariosUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.buscarUsuariosUseCase = buscarUsuariosUseCase;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@RequestBody RegistroUsuarioDTO dto) {
        Usuario usuario = new Usuario(
                null,
                dto.getNombre(),
                dto.getApellidoPaterno(),
                dto.getApellidoMaterno(),
                dto.getCorreo(),
                dto.getUsuario(),
                dto.getPassword(),
                dto.getFechaNacimiento()
        );

        Usuario registrado = registrarUsuarioUseCase.registrar(usuario);
        UsuarioResponseDTO response = new UsuarioResponseDTO(
                registrado.getId(),
                registrado.getNombre(),
                registrado.getApellidoPaterno(),
                registrado.getApellidoMaterno(),
                registrado.getUsuario()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarUsuarios(@RequestParam(name = "texto", required = false, defaultValue = "") String texto) {
        List<Usuario> resultados = buscarUsuariosUseCase.buscarPorTexto(texto);

        List<UsuarioResponseDTO> dtoList = resultados.stream()
                .map(u -> new UsuarioResponseDTO(
                        u.getId(),
                        u.getNombre(),
                        u.getApellidoPaterno(),
                        u.getApellidoMaterno(),
                        u.getUsuario()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
}