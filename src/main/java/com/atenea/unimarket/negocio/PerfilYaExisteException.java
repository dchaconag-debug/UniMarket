package com.atenea.unimarket.negocio;

public class PerfilYaExisteException extends RuntimeException {

    public PerfilYaExisteException() {
        super("El usuario ya tiene un perfil.");
    }
}
