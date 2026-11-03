package com.atenea.unimarket.presentacion.formulario;

import java.util.ArrayList;
import java.util.List;

import com.atenea.unimarket.datos.modelo.Servicio;
import com.atenea.unimarket.negocio.DatosServicio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ServicioFormulario {

    @NotNull(message = "Selecciona una categoría.")
    private Integer categoriaId;

    @NotBlank(message = "Ingresa un título.")
    @Size(max = Servicio.LARGO_TITULO, message = "El título puede tener máximo 100 caracteres.")
    private String titulo;

    @NotBlank(message = "Describe el servicio.")
    @Size(max = Servicio.LARGO_TEXTO, message = "La descripción puede tener máximo 5000 caracteres.")
    private String descripcion;

    @NotNull(message = "Ingresa la tarifa por hora.")
    @Positive(message = "La tarifa por hora debe ser mayor que cero.")
    private Integer tarifaPorHora;

    @NotNull(message = "Ingresa la intensidad horaria.")
    @Min(value = 1, message = "La intensidad horaria debe ser de al menos 1 hora por semana.")
    @Max(value = Servicio.INTENSIDAD_MAXIMA, message = "La intensidad horaria no puede superar 168 horas por semana.")
    private Integer intensidadHoraria;

    @Size(max = Servicio.LARGO_TEXTO, message = "La experiencia puede tener máximo 5000 caracteres.")
    private String experiencia;

    @Size(max = Servicio.LARGO_ESPECIALIDAD, message = "La especialidad puede tener máximo 100 caracteres.")
    private String especialidad;

    @Valid
    private List<FranjaFormulario> franjas = new ArrayList<>();

        /** A form prefilled with the current values of a service, to edit it. */
    public static ServicioFormulario desde(Servicio servicio) {
        ServicioFormulario formulario = new ServicioFormulario();
        formulario.categoriaId = servicio.getCategoria().getId();
        formulario.titulo = servicio.getTitulo();
        formulario.descripcion = servicio.getDescripcion();
        formulario.tarifaPorHora = servicio.getTarifaPorHora();
        formulario.intensidadHoraria = servicio.getIntensidadHoraria();
        formulario.experiencia = servicio.getExperiencia();
        formulario.especialidad = servicio.getEspecialidad();
        formulario.franjas = new ArrayList<>(
                servicio.getHorariosDisponibilidad().stream().map(FranjaFormulario::desde).toList());
        return formulario;
    }

    public DatosServicio aDatos() {
        return new DatosServicio(categoriaId, titulo, descripcion, tarifaPorHora, intensidadHoraria,
                experiencia, especialidad, franjas.stream().map(FranjaFormulario::aFranja).toList());
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(Integer tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    public Integer getIntensidadHoraria() {
        return intensidadHoraria;
    }

    public void setIntensidadHoraria(Integer intensidadHoraria) {
        this.intensidadHoraria = intensidadHoraria;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<FranjaFormulario> getFranjas() {
        return franjas;
    }

    public void setFranjas(List<FranjaFormulario> franjas) {
        this.franjas = franjas;
    }
}
