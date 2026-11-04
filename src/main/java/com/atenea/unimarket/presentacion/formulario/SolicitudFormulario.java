package com.atenea.unimarket.presentacion.formulario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** What the client sends when requesting a service: the hourly rate they propose. */
public class SolicitudFormulario {

    @NotNull(message = "Ingresa la tarifa por hora.")
    @Positive(message = "La tarifa por hora debe ser mayor que cero.")
    private Integer tarifaPorHora;

    public SolicitudFormulario() {
    }

    /** A form that starts with the published rate, so sending it as is accepts the published conditions. */
    public SolicitudFormulario(Integer tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    public Integer getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(Integer tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }
}
