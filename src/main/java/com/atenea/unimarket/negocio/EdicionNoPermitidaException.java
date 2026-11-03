package com.atenea.unimarket.negocio;

public class EdicionNoPermitidaException extends RuntimeException {

    public EdicionNoPermitidaException() {
        super("Solo el ofertador puede editar su servicio.");
    }
}
