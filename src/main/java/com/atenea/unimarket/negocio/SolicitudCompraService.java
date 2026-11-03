package com.atenea.unimarket.negocio;

import org.springframework.stereotype.Service;

import com.atenea.unimarket.datos.modelo.SolicitudCompra;

@Service
public class SolicitudCompraService {

    /** COMPRA-CLI-H01: the client requests a service with a rate (first round). */
    public SolicitudCompra crear(Integer clienteId, Integer servicioId, Integer tarifaPorHora) {
        throw new UnsupportedOperationException("Pendiente: COMPRA-CLI-H01");
    }

    /** COMPRA-CLI-H02: the other party answers with a new rate (and weekly hours if offerer). */
    public SolicitudCompra contraproponer(Integer solicitudId, Integer usuarioId, Integer tarifaPorHora,
            Integer intensidadHoraria) {
        throw new UnsupportedOperationException("Pendiente: COMPRA-CLI-H02");
    }

    /** The counterpart accepts the current proposal: the request becomes ACTIVA. */
    public SolicitudCompra aceptar(Integer solicitudId, Integer usuarioId) {
        throw new UnsupportedOperationException("Pendiente: COMPRA-CLI-H02");
    }

    /** The offerer rejects without a counterproposal: the request becomes CANCELADA. */
    public SolicitudCompra rechazar(Integer solicitudId, Integer usuarioId) {
        throw new UnsupportedOperationException("Pendiente: COMPRA-CLI-H01");
    }
}