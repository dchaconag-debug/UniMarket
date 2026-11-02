package com.atenea.unimarket.datos.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atenea.unimarket.datos.modelo.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    Optional<Perfil> findByUsuarioId(Integer usuarioId);

    boolean existsByUsuarioId(Integer usuarioId);
}
