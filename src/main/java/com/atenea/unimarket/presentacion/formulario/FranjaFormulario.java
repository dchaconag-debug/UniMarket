package com.atenea.unimarket.presentacion.formulario;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.atenea.unimarket.datos.modelo.DiaSemana;
import com.atenea.unimarket.datos.modelo.FranjaHorario;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class FranjaFormulario {

    @NotNull(message = "Selecciona el día.")
    private DiaSemana dia;

    @NotNull(message = "Indica la hora de inicio.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime inicio;

    @NotNull(message = "Indica la hora de fin.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime fin;

    @AssertTrue(message = "La hora de inicio debe ser anterior a la hora de fin.")
    public boolean isRangoValido() {
        return inicio == null || fin == null || inicio.isBefore(fin);
    }

    public FranjaHorario aFranja() {
        return new FranjaHorario(dia, inicio, fin);
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }
}
