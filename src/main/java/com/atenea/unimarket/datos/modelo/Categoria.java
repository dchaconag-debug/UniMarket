package com.atenea.unimarket.datos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Fixed list created by the V3 migration until an admin role manages categories. */
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    protected Categoria() {
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
