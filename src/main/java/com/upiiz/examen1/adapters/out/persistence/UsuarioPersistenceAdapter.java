package com.upiiz.examen1.adapters.out.persistence;

import com.upiiz.examen1.domain.model.Usuario;
import com.upiiz.examen1.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioPersistenceAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioEntity entity = toEntity(usuario);
        UsuarioEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return jpaRepository.existsByCorreo(correo);
    }

    @Override
    public boolean existePorUsuario(String usuario) {
        return jpaRepository.existsByUsuario(usuario);
    }

    @Override
    public List<Usuario> buscarPorTexto(String texto) {
        return jpaRepository.buscarPorTextoParcial(texto)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private UsuarioEntity toEntity(Usuario domain) {
        return new UsuarioEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getApellidoPaterno(),
                domain.getApellidoMaterno(),
                domain.getCorreo(),
                domain.getUsuario(),
                domain.getPassword(),
                domain.getFechaNacimiento()
        );
    }

    private Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidoPaterno(),
                entity.getApellidoMaterno(),
                entity.getCorreo(),
                entity.getUsuario(),
                entity.getPassword(),
                entity.getFechaNacimiento()
        );
    }
}