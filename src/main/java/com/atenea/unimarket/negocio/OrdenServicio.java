package com.atenea.unimarket.negocio;

import org.springframework.data.domain.Sort;

public enum OrdenServicio {
    ANTIGUOS("Más antiguos", Sort.by(Sort.Direction.ASC, "fechaPublicacion", "id")),
    RECIENTES("Más recientes", Sort.by(Sort.Direction.DESC, "fechaPublicacion", "id")),
    TARIFA_ASC("Tarifa: de menor a mayor", Sort.by(Sort.Direction.ASC, "tarifaPorHora")
            .and(Sort.by(Sort.Direction.DESC, "fechaPublicacion"))),
    TARIFA_DESC("Tarifa: de mayor a menor", Sort.by(Sort.Direction.DESC, "tarifaPorHora")
            .and(Sort.by(Sort.Direction.DESC, "fechaPublicacion")));
    // Orden por calificación: pendiente hasta que exista el modelo de reseñas.

    private final String etiqueta;
    private final Sort sort;

    OrdenServicio(String etiqueta, Sort sort) {
        this.etiqueta = etiqueta;
        this.sort = sort;
    }

    public String getEtiqueta() { return etiqueta; }
    public Sort getSort() { return sort; }

    /** Valor desconocido o ausente: orden por defecto. */
    public static OrdenServicio desde(String nombre) {
        for (OrdenServicio orden : values()) {
            if (orden.name().equals(nombre)) {
                return orden;
            }
        }
        return ANTIGUOS;
    }
}
