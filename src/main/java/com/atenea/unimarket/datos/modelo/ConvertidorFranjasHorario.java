package com.atenea.unimarket.datos.modelo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

/**
 * Stores availability as a JSON array in a TEXT column, since SQLite has no JSON type:
 * {@code [{"dia":"LUNES","inicio":"08:00","fin":"10:00"}]}. No slots are stored as NULL.
 */
@Converter
public class ConvertidorFranjasHorario implements AttributeConverter<List<FranjaHorario>, String> {

    private static final JsonMapper JSON = JsonMapper.builder().build();
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    record FranjaJson(DiaSemana dia, String inicio, String fin) {
    }

    @Override
    public String convertToDatabaseColumn(List<FranjaHorario> franjas) {
        if (franjas == null || franjas.isEmpty()) {
            return null;
        }
        return JSON.writeValueAsString(franjas.stream()
                .map(f -> new FranjaJson(f.dia(), HORA.format(f.inicio()), HORA.format(f.fin())))
                .toList());
    }

    @Override
    public List<FranjaHorario> convertToEntityAttribute(String json) {
        if (json == null) {
            return List.of();
        }
        return JSON.readValue(json, new TypeReference<List<FranjaJson>>() { }).stream()
                .map(f -> new FranjaHorario(f.dia(), LocalTime.parse(f.inicio(), HORA), LocalTime.parse(f.fin(), HORA)))
                .toList();
    }
}
