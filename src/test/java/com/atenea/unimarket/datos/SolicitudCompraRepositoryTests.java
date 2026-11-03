package com.atenea.unimarket.datos;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import com.atenea.unimarket.datos.modelo.Categoria;
import com.atenea.unimarket.datos.modelo.EstadoSolicitud;
import com.atenea.unimarket.datos.modelo.Propuesta;
import com.atenea.unimarket.datos.modelo.Servicio;
import com.atenea.unimarket.datos.modelo.SolicitudCompra;
import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.datos.repositorio.CategoriaRepository;
import com.atenea.unimarket.datos.repositorio.PerfilRepository;
import com.atenea.unimarket.datos.repositorio.PropuestaRepository;
import com.atenea.unimarket.datos.repositorio.ServicioRepository;
import com.atenea.unimarket.datos.repositorio.SolicitudCompraRepository;
import com.atenea.unimarket.datos.repositorio.UsuarioRepository;
import com.atenea.unimarket.negocio.PerfilService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = "spring.datasource.url=jdbc:sqlite:target/solicitud-repository-test.db")
class SolicitudCompraRepositoryTests {

    @Autowired
    SolicitudCompraRepository solicitudes;

    @Autowired
    PropuestaRepository propuestas;

    @Autowired
    ServicioRepository servicios;

    @Autowired
    CategoriaRepository categorias;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    UsuarioRepository usuarios;

    @Autowired
    PerfilService perfiles;

    Usuario ofertador;
    Usuario cliente;
    Servicio servicio;

    @BeforeEach
    void preparar() {
        propuestas.deleteAll();
        solicitudes.deleteAll();
        servicios.deleteAll();
        perfilRepository.deleteAll();
        usuarios.deleteAll();
        ofertador = perfiles.registrar("sub-ana", "ana@unal.edu.co", "Ana", "3001234567", List.of()).getUsuario();
        cliente = perfiles.registrar("sub-luis", "luis@unal.edu.co", "Luis", "3009876543", List.of()).getUsuario();
        Categoria categoria = categorias.findAllByOrderByNombreAsc().get(0);
        servicio = servicios.save(new Servicio(ofertador, categoria, "Cálculo I", "Tutorías de cálculo.",
                25000, 4, null, null, List.of(), Instant.now()));
    }

    @Test
    void lasSolicitudesNacenNegociandoYSeDetectanComoEnCurso() {
        assertThat(solicitudes.existsByServicioIdAndEstadoIn(servicio.getId(),
                List.of(EstadoSolicitud.NEGOCIANDO, EstadoSolicitud.ACTIVA))).isFalse();

        SolicitudCompra solicitud = solicitudes.save(new SolicitudCompra(cliente, servicio, Instant.now()));

        assertThat(solicitudes.findById(solicitud.getId()).orElseThrow().getEstado())
                .isEqualTo(EstadoSolicitud.NEGOCIANDO);
        assertThat(solicitudes.existsByServicioIdAndEstadoIn(servicio.getId(),
                List.of(EstadoSolicitud.NEGOCIANDO, EstadoSolicitud.ACTIVA))).isTrue();
    }

    @Test
    void unaSolicitudCanceladaNoCuentaComoEnCurso() {
        SolicitudCompra solicitud = new SolicitudCompra(cliente, servicio, Instant.now());
        solicitud.cancelar(Instant.now());
        solicitudes.save(solicitud);

        assertThat(solicitudes.existsByServicioIdAndEstadoIn(servicio.getId(),
                List.of(EstadoSolicitud.NEGOCIANDO, EstadoSolicitud.ACTIVA))).isFalse();
    }

    @Test
    void laSolicitudSoloCambiaDeEstadoDesdeNegociando() {
        SolicitudCompra solicitud = new SolicitudCompra(cliente, servicio, Instant.now());
        solicitud.activar(Instant.now());

        assertThat(solicitud.getEstado()).isEqualTo(EstadoSolicitud.ACTIVA);
        assertThatThrownBy(() -> solicitud.cancelar(Instant.now())).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void laPropuestaVigenteEsLaUltimaRondaYElHistorialSale_enOrden() {
        SolicitudCompra solicitud = solicitudes.save(new SolicitudCompra(cliente, servicio, Instant.now()));
        propuestas.save(new Propuesta(solicitud, cliente, 20000, 4, 1, Instant.now()));
        propuestas.save(new Propuesta(solicitud, ofertador, 23000, 3, 2, Instant.now()));
        propuestas.save(new Propuesta(solicitud, cliente, 22000, 3, 3, Instant.now()));

        assertThat(propuestas.findBySolicitudIdOrderByNumeroRondaAsc(solicitud.getId()))
                .extracting(Propuesta::getNumeroRonda).containsExactly(1, 2, 3);
        Propuesta vigente = propuestas.findFirstBySolicitudIdOrderByNumeroRondaDesc(solicitud.getId()).orElseThrow();
        assertThat(vigente.getNumeroRonda()).isEqualTo(3);
        assertThat(vigente.getAutor().getId()).isEqualTo(cliente.getId());
        assertThat(vigente.getTarifaPorHora()).isEqualTo(22000);
    }

    @Test
    void laBaseDeDatosNoPermiteRepetirLaRondaNiTarifasInvalidas() {
        SolicitudCompra solicitud = solicitudes.save(new SolicitudCompra(cliente, servicio, Instant.now()));
        propuestas.save(new Propuesta(solicitud, cliente, 20000, 4, 1, Instant.now()));

        assertThatThrownBy(() -> propuestas.save(new Propuesta(solicitud, ofertador, 23000, 3, 1, Instant.now())))
                .isInstanceOf(DataAccessException.class);
        assertThatThrownBy(() -> new Propuesta(solicitud, cliente, 0, 4, 2, Instant.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}