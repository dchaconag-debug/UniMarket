package com.atenea.unimarket.datos.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atenea.unimarket.datos.modelo.Propuesta;

public interface PropuestaRepository extends JpaRepository<Propuesta, Integer> {

    List<Propuesta> findBySolicitudIdOrderByNumeroRondaAsc(Integer solicitudId);

    Optional<Propuesta> findFirstBySolicitudIdOrderByNumeroRondaDesc(Integer solicitudId);
}