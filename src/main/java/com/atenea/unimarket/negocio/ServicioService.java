package com.atenea.unimarket.negocio;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atenea.unimarket.datos.modelo.Categoria;
import com.atenea.unimarket.datos.modelo.EstadoServicio;
import com.atenea.unimarket.datos.modelo.Servicio;
import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.datos.repositorio.CategoriaRepository;
import com.atenea.unimarket.datos.repositorio.ServicioRepository;
import com.atenea.unimarket.datos.repositorio.UsuarioRepository;

@Service
public class ServicioService {

    private final ServicioRepository servicios;
    private final UsuarioRepository usuarios;
    private final CategoriaRepository categorias;
    private final Clock reloj;

    public ServicioService(ServicioRepository servicios, UsuarioRepository usuarios,
            CategoriaRepository categorias, Clock reloj) {
        this.servicios = servicios;
        this.usuarios = usuarios;
        this.categorias = categorias;
        this.reloj = reloj;
    }

    /**
     * Publishes a service for the offerer, available right away. Every registered user can
     * publish: Google login is the institutional verification (CU-03) and registration always
     * creates the profile.
     */
    @Transactional
    public Servicio publicar(Integer ofertadorId, DatosServicio datos) {
        Usuario ofertador = usuarios.findById(ofertadorId)
                .orElseThrow(() -> new IllegalArgumentException("El ofertador no existe."));
        Categoria categoria = Optional.ofNullable(datos.categoriaId())
                .flatMap(categorias::findById)
                .orElseThrow(CategoriaNoEncontradaException::new);
        Servicio servicio = new Servicio(ofertador, categoria,
                obligatorio(datos.titulo(), "título"),
                obligatorio(datos.descripcion(), "descripción"),
                requerirNumero(datos.tarifaPorHora(), "tarifa por hora"),
                requerirNumero(datos.intensidadHoraria(), "intensidad horaria"),
                opcional(datos.experiencia()),
                opcional(datos.especialidad()),
                datos.horarios() == null ? List.of() : datos.horarios(),
                Instant.now(reloj));
        return servicios.save(servicio);
    }

    @Transactional(readOnly = true)
    public Optional<Servicio> buscarPorId(Integer id) {
        return servicios.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Servicio> disponiblesEnCategoria(Integer categoriaId) {
        return servicios.findByCategoriaIdAndEstadoOrderByFechaPublicacionAsc(categoriaId, EstadoServicio.DISPONIBLE);
    }

    @Transactional(readOnly = true)
    public List<Servicio> disponiblesDeOfertador(Integer ofertadorId) {
        return servicios.findByOfertadorIdAndEstadoOrderByFechaPublicacionAsc(ofertadorId, EstadoServicio.DISPONIBLE);
    }

    @Transactional(readOnly = true)
    public List<Categoria> categorias() {
        return categorias.findAllByOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> buscarCategoria(Integer id) {
        return categorias.findById(id);
    }

    private static String obligatorio(String valor, String campo) {
        String limpio = opcional(valor);
        if (limpio == null) {
            throw new IllegalArgumentException("Falta " + campo + ".");
        }
        return limpio;
    }

    private static int requerirNumero(Integer valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("Falta " + campo + ".");
        }
        return valor;
    }

    private static String opcional(String valor) {
        return valor == null || valor.isBlank() ? null : valor.strip();
    }
}
