package com.atenea.unimarket.negocio;

public class ServicioNoEncontradoException extends RuntimeException {

    public ServicioNoEncontradoException() {
        super("El servicio no existe.");
    }
}