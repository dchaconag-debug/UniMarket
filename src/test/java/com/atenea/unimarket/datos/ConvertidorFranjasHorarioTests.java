package com.atenea.unimarket.datos;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.atenea.unimarket.datos.modelo.ConvertidorFranjasHorario;
import com.atenea.unimarket.datos.modelo.DiaSemana;
import com.atenea.unimarket.datos.modelo.FranjaHorario;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConvertidorFranjasHorarioTests {

    private final ConvertidorFranjasHorario convertidor = new ConvertidorFranjasHorario();

    @Test
    void guardaLasFranjasComoArregloJson() {
        List<FranjaHorario> franjas = List.of(
                new FranjaHorario(DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(10, 0)),
                new FranjaHorario(DiaSemana.MIERCOLES, LocalTime.of(14, 30), LocalTime.of(16, 0)));

        String json = convertidor.convertToDatabaseColumn(franjas);

        assertThat(json).isEqualTo("[{\"dia\":\"LUNES\",\"inicio\":\"08:00\",\"fin\":\"10:00\"},"
                + "{\"dia\":\"MIERCOLES\",\"inicio\":\"14:30\",\"fin\":\"16:00\"}]");
        assertThat(convertidor.convertToEntityAttribute(json)).isEqualTo(franjas);
    }

    @Test
    void sinFranjasGuardaNull() {
        assertThat(convertidor.convertToDatabaseColumn(List.of())).isNull();
        assertThat(convertidor.convertToEntityAttribute(null)).isEmpty();
    }

    @Test
    void rechazaUnaFranjaQueTerminaAntesDeEmpezar() {
        assertThatThrownBy(() -> new FranjaHorario(DiaSemana.LUNES, LocalTime.of(10, 0), LocalTime.of(10, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
