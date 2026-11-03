package com.atenea.unimarket.presentacion.formulario;

import java.time.LocalTime;
import java.util.List;

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

    /**
     * Applies an add/remove slot button from a form without JavaScript. Anything that is not a
     * valid row number is ignored instead of failing the request.
     */
    public static void aplicarAccion(List<FranjaFormulario> franjas, String accion) {
        if (accion.equals("agregarFranja")) {
            franjas.add(new FranjaFormulario());
        } else if (accion.startsWith("quitarFranja:")) {
            String indice = accion.substring("quitarFranja:".length());
            if (indice.matches("\\d{1,4}") && Integer.parseInt(indice) < franjas.size()) {
                franjas.remove(Integer.parseInt(indice));
            }
        }
    }

    @AssertTrue(message = "La hora de inicio debe ser anterior a la hora de fin.")
    public boolean isRangoValido() {
        return inicio == null || fin == null || inicio.isBefore(fin);
    }

        public static FranjaFormulario desde(FranjaHorario franja) {
        FranjaFormulario formulario = new FranjaFormulario();
        formulario.setDia(franja.dia());
        formulario.setInicio(franja.inicio());
        formulario.setFin(franja.fin());
        return formulario;
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
