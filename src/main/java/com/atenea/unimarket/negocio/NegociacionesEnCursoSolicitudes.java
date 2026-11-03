package com.atenea.unimarket.negocio;

import java.util.List;

import org.springframework.stereotype.Component;

import com.atenea.unimarket.datos.modelo.EstadoSolicitud;
import com.atenea.unimarket.datos.repositorio.SolicitudCompraRepository;

/** A service has negotiations in progress while it has a request in NEGOCIANDO or ACTIVA. */
@Component
class NegociacionesEnCursoSolicitudes implements NegociacionesEnCurso {

    private final SolicitudCompraRepository solicitudes;

    NegociacionesEnCursoSolicitudes(SolicitudCompraRepository solicitudes) {
        this.solicitudes = solicitudes;
    }

    @Override
    public boolean hayParaServicio(Integer servicioId) {
        return solicitudes.existsByServicioIdAndEstadoIn(
                servicioId, List.of(EstadoSolicitud.NEGOCIANDO, EstadoSolicitud.ACTIVA));
    }
}