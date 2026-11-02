package com.atenea.unimarket.datos.modelo;

import java.time.LocalTime;
import java.util.Objects;

/** A weekly availability slot, for example Monday from 08:00 to 10:00. */
public record FranjaHorario(DiaSemana dia, LocalTime inicio, LocalTime fin) {

    public FranjaHorario {
        Objects.requireNonNull(dia, "dia");
        Objects.requireNonNull(inicio, "inicio");
        Objects.requireNonNull(fin, "fin");
        if (!inicio.isBefore(fin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin.");
        }
    }
}
