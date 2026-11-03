package com.atenea.unimarket.negocio;

/** The rate or weekly hours cannot change while the service has negotiations in progress. */
public class CondicionesBloqueadasException extends RuntimeException {

    public CondicionesBloqueadasException() {
        super("No puedes cambiar la tarifa ni la intensidad horaria mientras el servicio tenga negociaciones en curso.");
    }
}
