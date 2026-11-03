package com.atenea.unimarket.datos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atenea.unimarket.datos.modelo.EstadoServicio;
import com.atenea.unimarket.datos.modelo.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    List<Servicio> findByCategoriaIdAndEstadoOrderByFechaPublicacionAsc(Integer categoriaId, EstadoServicio estado);

    List<Servicio> findByOfertadorIdAndEstadoOrderByFechaPublicacionAsc(Integer ofertadorId, EstadoServicio estado);

    List<Servicio> findByEstado(EstadoServicio estado, Sort sort);
}
