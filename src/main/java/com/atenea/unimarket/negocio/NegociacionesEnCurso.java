package com.atenea.unimarket.negocio;

/** Tells whether a service has purchase requests still being negotiated */
public interface NegociacionesEnCurso {

    boolean hayParaServicio(Integer servicioId);
}
