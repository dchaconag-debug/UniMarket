package com.atenea.unimarket.datos.modelo;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true, updatable = false)
    private Usuario usuario;

    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;

    @Convert(converter = ConvertidorFranjasHorario.class)
    @Column(name = "horarios_disponibilidad", columnDefinition = "text")
    private List<FranjaHorario> horariosDisponibilidad = List.of();

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant fechaActualizacion;

    protected Perfil() {
    }

    public Perfil(Usuario usuario, String telefonoContacto, List<FranjaHorario> horariosDisponibilidad, Instant ahora) {
        this.usuario = usuario;
        this.telefonoContacto = telefonoContacto;
        this.horariosDisponibilidad = List.copyOf(horariosDisponibilidad);
        this.fechaCreacion = ahora;
        this.fechaActualizacion = ahora;
    }

    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public List<FranjaHorario> getHorariosDisponibilidad() {
        return horariosDisponibilidad;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }
}
