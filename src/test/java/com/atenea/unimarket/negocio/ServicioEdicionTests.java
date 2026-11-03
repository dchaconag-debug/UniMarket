package com.atenea.unimarket.negocio;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.atenea.unimarket.datos.modelo.Categoria;
import com.atenea.unimarket.datos.modelo.DiaSemana;
import com.atenea.unimarket.datos.modelo.EstadoServicio;
import com.atenea.unimarket.datos.modelo.FranjaHorario;
import com.atenea.unimarket.datos.modelo.Servicio;
import com.atenea.unimarket.datos.repositorio.PerfilRepository;
import com.atenea.unimarket.datos.repositorio.ServicioRepository;
import com.atenea.unimarket.datos.repositorio.UsuarioRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.datasource.url=jdbc:sqlite:target/servicio-edicion-test.db")
class ServicioEdicionTests {

    @Autowired
    ServicioService servicio;

    @Autowired
    PerfilService perfiles;

    @Autowired
    ServicioRepository servicios;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    UsuarioRepository usuarios;

    @MockitoBean
    NegociacionesEnCurso negociaciones;

    Integer anaId;
    Integer betoId;
    Integer servicioId;
    Categoria tutorias;
    Categoria diseno;

    @BeforeEach
    void preparar() {
        servicios.deleteAll();
        perfilRepository.deleteAll();
        usuarios.deleteAll();
        anaId = perfiles.registrar("sub-ana", "ana@unal.edu.co", "Ana", "3001234567", List.of()).getUsuario().getId();
        betoId = perfiles.registrar("sub-beto", "beto@unal.edu.co", "Beto", "3109876543", List.of()).getUsuario().getId();
        tutorias = categoria("Tutorías");
        diseno = categoria("Diseño");
        servicioId = servicio.publicar(anaId, datos(tutorias, 25000, 4)).getId();
    }

    @Test
    void elOfertadorEditaTodosLosCamposSinNegociaciones() {
        List<FranjaHorario> horarios = List.of(new FranjaHorario(DiaSemana.LUNES, LocalTime.of(8, 0), LocalTime.of(10, 0)));

        servicio.editar(servicioId, anaId, new DatosServicio(diseno.getId(), "  Logos  ", "Diseño de logos.",
                30000, 6, "5 años", "Branding", horarios));

        Servicio guardado = servicios.findById(servicioId).orElseThrow();
        assertThat(guardado.getCategoria().getId()).isEqualTo(diseno.getId());
        assertThat(guardado.getTitulo()).isEqualTo("Logos");
        assertThat(guardado.getDescripcion()).isEqualTo("Diseño de logos.");
        assertThat(guardado.getTarifaPorHora()).isEqualTo(30000);
        assertThat(guardado.getIntensidadHoraria()).isEqualTo(6);
        assertThat(guardado.getExperiencia()).isEqualTo("5 años");
        assertThat(guardado.getEspecialidad()).isEqualTo("Branding");
        assertThat(guardado.getHorariosDisponibilidad()).isEqualTo(horarios);
    }

    @Test
    void editarNoCambiaOfertadorEstadoNiFechaDePublicacion() {
        Servicio antes = servicios.findById(servicioId).orElseThrow();

        servicio.editar(servicioId, anaId, datos(tutorias, 26000, 5));

        Servicio despues = servicios.findById(servicioId).orElseThrow();
        assertThat(despues.getOfertador().getId()).isEqualTo(anaId);
        assertThat(despues.getEstado()).isEqualTo(EstadoServicio.DISPONIBLE);
        assertThat(despues.getFechaPublicacion()).isEqualTo(antes.getFechaPublicacion());
    }

    @Test
    void soloElOfertadorPuedeEditar() {
        assertThatThrownBy(() -> servicio.editar(servicioId, betoId, datos(tutorias, 1000, 1)))
                .isInstanceOf(EdicionNoPermitidaException.class);

        assertThat(servicios.findById(servicioId).orElseThrow().getTarifaPorHora()).isEqualTo(25000);
    }

    @Test
    void editarUnServicioInexistenteFalla() {
        assertThatThrownBy(() -> servicio.editar(999999, anaId, datos(tutorias, 25000, 4)))
                .isInstanceOf(ServicioNoEncontradoException.class);
    }

    @Test
    void aplicaLasMismasValidacionesQuePublicar() {
        assertThatThrownBy(() -> servicio.editar(servicioId, anaId, datos(tutorias, 0, 4)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> servicio.editar(servicioId, anaId, datos(tutorias, 25000, 169)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> servicio.editar(servicioId, anaId,
                new DatosServicio(tutorias.getId(), " ", "Descripción", 25000, 4, null, null, List.of())))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> servicio.editar(servicioId, anaId,
                new DatosServicio(999999, "Cálculo I", "Descripción", 25000, 4, null, null, List.of())))
                .isInstanceOf(CategoriaNoEncontradaException.class);
    }

    @Test
    void conNegociacionesEnCursoNoSePuedeCambiarLaTarifa() {
        when(negociaciones.hayParaServicio(servicioId)).thenReturn(true);

        assertThatThrownBy(() -> servicio.editar(servicioId, anaId, datos(tutorias, 30000, 4)))
                .isInstanceOf(CondicionesBloqueadasException.class);

        assertThat(servicios.findById(servicioId).orElseThrow().getTarifaPorHora()).isEqualTo(25000);
    }

    @Test
    void conNegociacionesEnCursoNoSePuedeCambiarLaIntensidad() {
        when(negociaciones.hayParaServicio(servicioId)).thenReturn(true);

        assertThatThrownBy(() -> servicio.editar(servicioId, anaId, datos(tutorias, 25000, 8)))
                .isInstanceOf(CondicionesBloqueadasException.class);

        assertThat(servicios.findById(servicioId).orElseThrow().getIntensidadHoraria()).isEqualTo(4);
    }

    @Test
    void conNegociacionesEnCursoSePuedenCambiarLosDemasCampos() {
        when(negociaciones.hayParaServicio(servicioId)).thenReturn(true);

        servicio.editar(servicioId, anaId, new DatosServicio(diseno.getId(), "Nuevo título", "Nueva descripción",
                25000, 4, "Experiencia", "Especialidad", List.of()));

        Servicio guardado = servicios.findById(servicioId).orElseThrow();
        assertThat(guardado.getTitulo()).isEqualTo("Nuevo título");
        assertThat(guardado.getDescripcion()).isEqualTo("Nueva descripción");
        assertThat(guardado.getCategoria().getId()).isEqualTo(diseno.getId());
        assertThat(guardado.getTarifaPorHora()).isEqualTo(25000);
        assertThat(guardado.getIntensidadHoraria()).isEqualTo(4);
    }

    @Test
    void condicionesBloqueadasReflejaLasNegociacionesEnCurso() {
        assertThat(servicio.condicionesBloqueadas(servicioId)).isFalse();
        when(negociaciones.hayParaServicio(servicioId)).thenReturn(true);
        assertThat(servicio.condicionesBloqueadas(servicioId)).isTrue();
    }

    private Categoria categoria(String nombre) {
        return servicio.categorias().stream().filter(c -> c.getNombre().equals(nombre)).findFirst().orElseThrow();
    }

    private DatosServicio datos(Categoria categoria, int tarifa, int intensidad) {
        return new DatosServicio(categoria.getId(), "Cálculo I", "Tutorías de cálculo diferencial.",
                tarifa, intensidad, null, null, List.of());
    }
}
