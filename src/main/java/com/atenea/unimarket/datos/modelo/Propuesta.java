package com.atenea.unimarket.datos.modelo;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "propuesta")
public class Propuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "solicitud_id", nullable = false, updatable = false)
    private SolicitudCompra solicitud;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false, updatable = false)
    private Usuario autor;

    /** Whole Colombian pesos per hour. */
    @Column(name = "tarifa_por_hora", nullable = false, updatable = false)
    private Integer tarifaPorHora;

    /** Hours per week. Always filled: a client proposal copies the current value. */
    @Column(name = "intensidad_horaria", nullable = false, updatable = false)
    private Integer intensidadHoraria;

    @Column(name = "numero_ronda", nullable = false, updatable = false)
    private Integer numeroRonda;

    @Column(name = "fecha_envio", nullable = false, updatable = false)
    private Instant fechaEnvio;

    protected Propuesta() {
    }

    public Propuesta(SolicitudCompra solicitud, Usuario autor, int tarifaPorHora, int intensidadHoraria,
            int numeroRonda, Instant fechaEnvio) {
        if (tarifaPorHora <= 0) {
            throw new IllegalArgumentException("La tarifa por hora debe ser mayor que cero.");
        }
        if (intensidadHoraria < 1 || intensidadHoraria > Servicio.INTENSIDAD_MAXIMA) {
            throw new IllegalArgumentException("La intensidad horaria debe estar entre 1 y 168 horas por semana.");
        }
        if (numeroRonda < 1) {
            throw new IllegalArgumentException("El número de ronda debe ser al menos 1.");
        }
        this.solicitud = solicitud;
        this.autor = autor;
        this.tarifaPorHora = tarifaPorHora;
        this.intensidadHoraria = intensidadHoraria;
        this.numeroRonda = numeroRonda;
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getId() {
        return id;
    }

    public SolicitudCompra getSolicitud() {
        return solicitud;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Integer getTarifaPorHora() {
        return tarifaPorHora;
    }

    public Integer getIntensidadHoraria() {
        return intensidadHoraria;
    }

    public Integer getNumeroRonda() {
        return numeroRonda;
    }

    public Instant getFechaEnvio() {
        return fechaEnvio;
    }
}