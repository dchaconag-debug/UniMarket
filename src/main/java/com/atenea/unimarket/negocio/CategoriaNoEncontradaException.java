package com.atenea.unimarket.negocio;

public class CategoriaNoEncontradaException extends RuntimeException {

    public CategoriaNoEncontradaException() {
        super("La categoría no existe.");
    }
}
