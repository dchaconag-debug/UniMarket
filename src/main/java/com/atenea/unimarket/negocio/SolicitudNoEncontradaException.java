package com.atenea.unimarket.negocio;

public class SolicitudNoEncontradaException extends RuntimeException {

    public SolicitudNoEncontradaException() {
        super("La solicitud no existe.");
    }
}
