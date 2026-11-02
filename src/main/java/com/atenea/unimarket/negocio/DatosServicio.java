package com.atenea.unimarket.negocio;

import java.util.List;

import com.atenea.unimarket.datos.modelo.FranjaHorario;

/** What an offerer fills in to publish a service. */
public record DatosServicio(Integer categoriaId, String titulo, String descripcion, Integer tarifaPorHora,
        Integer intensidadHoraria, String experiencia, String especialidad, List<FranjaHorario> horarios) {
}
