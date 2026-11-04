package com.atenea.unimarket.negocio;

/** The action breaks a rule of the purchase flow; the message explains which one to the user. */
public class SolicitudNoPermitidaException extends RuntimeException {

    public SolicitudNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
