package com.atenea.unimarket.datos.repositorio;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atenea.unimarket.datos.modelo.EstadoSolicitud;
import com.atenea.unimarket.datos.modelo.SolicitudCompra;

public interface SolicitudCompraRepository extends JpaRepository<SolicitudCompra, Integer> {

    boolean existsByServicioIdAndEstadoIn(Integer servicioId, Collection<EstadoSolicitud> estados);

    boolean existsByClienteIdAndServicioIdAndEstado(Integer clienteId, Integer servicioId, EstadoSolicitud estado);

     List<SolicitudCompra> findByEstado(EstadoSolicitud estado);
}