package com.atenea.unimarket.negocio;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

@SpringBootTest(properties = "spring.datasource.url=jdbc:sqlite:target/servicio-service-test.db")
class ServicioServiceTests {

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

    @Autowired
    org.springframework.jdbc.core.JdbcTemplate jdbc;

    Integer anaId;
    Categoria tutorias;

    @BeforeEach
    void preparar() {
        servicios.deleteAll();
        perfilRepository.deleteAll();
        usuarios.deleteAll();
        anaId = perfiles.registrar("sub-ana", "ana@unal.edu.co", "Ana", "3001234567", List.of()).getUsuario().getId();
        tutorias = servicio.categorias().stream().filter(c -> c.getNombre().equals("Tutorías")).findFirst().orElseThrow();
    }

    @Test
    void lasCategoriasVienenDeLaMigracion() {
        assertThat(servicio.categorias()).extracting(Categoria::getNombre).containsExactly(
                "Asesoría de tesis y trabajos", "Clases particulares", "Diseño", "Escritura", "Tutorías");
    }

    @Test
    void publicarCreaElServicioDisponibleYAsociado() {
        List<FranjaHorario> horarios = List.of(new FranjaHorario(DiaSemana.SABADO, LocalTime.of(9, 0), LocalTime.of(12, 0)));

        Servicio publicado = servicio.publicar(anaId, new DatosServicio(tutorias.getId(), "  Cálculo I  ",
                "Tutorías de cálculo diferencial.", 25000, 4, " ", "Matemáticas", horarios));

        Servicio guardado = servicios.findById(publicado.getId()).orElseThrow();
        assertThat(guardado.getEstado()).isEqualTo(EstadoServicio.DISPONIBLE);
        assertThat(guardado.getOfertador().getId()).isEqualTo(anaId);
        assertThat(guardado.getCategoria().getId()).isEqualTo(tutorias.getId());
        assertThat(guardado.getTitulo()).isEqualTo("Cálculo I");
        assertThat(guardado.getTarifaPorHora()).isEqualTo(25000);
        assertThat(guardado.getIntensidadHoraria()).isEqualTo(4);
        assertThat(guardado.getExperiencia()).isNull();
        assertThat(guardado.getEspecialidad()).isEqualTo("Matemáticas");
        assertThat(guardado.getHorariosDisponibilidad()).isEqualTo(horarios);
        assertThat(guardado.getFechaPublicacion()).isNotNull();

        assertThat(servicio.disponiblesEnCategoria(tutorias.getId())).extracting(Servicio::getId).containsExactly(publicado.getId());
        assertThat(servicio.disponiblesDeOfertador(anaId)).extracting(Servicio::getId).containsExactly(publicado.getId());
    }

    @Test
    void rechazaTarifaMenorOIgualACeroEIntensidadFueraDeRango() {
        for (int tarifa : new int[] {0, -1000}) {
            assertThatThrownBy(() -> servicio.publicar(anaId, datos(tarifa, 4)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        for (int intensidad : new int[] {0, 169}) {
            assertThatThrownBy(() -> servicio.publicar(anaId, datos(25000, intensidad)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        assertThat(servicios.count()).isZero();
    }

    @Test
    void rechazaCategoriaInexistenteYCamposObligatoriosVacios() {
        assertThatThrownBy(() -> servicio.publicar(anaId,
                new DatosServicio(9999, "T", "D", 25000, 4, null, null, List.of())))
                .isInstanceOf(CategoriaNoEncontradaException.class);
        assertThatThrownBy(() -> servicio.publicar(anaId,
                new DatosServicio(tutorias.getId(), " ", "D", 25000, 4, null, null, List.of())))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> servicio.publicar(anaId,
                new DatosServicio(tutorias.getId(), "T", "D", null, 4, null, null, List.of())))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(servicios.count()).isZero();
    }

    @Test
    void rechazaTextosMasLargosQueElLimite() {
        String largo = "x".repeat(101);
        assertThatThrownBy(() -> servicio.publicar(anaId,
                new DatosServicio(tutorias.getId(), largo, "D", 25000, 4, null, null, List.of())))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> servicio.publicar(anaId,
                new DatosServicio(tutorias.getId(), "T", "D", 25000, 4, null, largo, List.of())))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> servicio.publicar(anaId,
                new DatosServicio(tutorias.getId(), "T", "x".repeat(5001), 25000, 4, null, null, List.of())))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(servicios.count()).isZero();
    }

    @Test
    void laBaseDeDatosTambienLimitaLosTextos() {
        Integer categoria = tutorias.getId();
        String sql = "INSERT INTO servicio (ofertador_id, categoria_id, titulo, descripcion, tarifa_por_hora,"
                + " intensidad_horaria, especialidad, fecha_publicacion, estado) VALUES (?, ?, ?, 'D', 25000, 4, ?, 0, 'DISPONIBLE')";
        assertThatThrownBy(() -> jdbc.update(sql, anaId, categoria, "x".repeat(101), null))
                .isInstanceOf(org.springframework.dao.DataAccessException.class);
        assertThatThrownBy(() -> jdbc.update(sql, anaId, categoria, "T", "x".repeat(101)))
                .isInstanceOf(org.springframework.dao.DataAccessException.class);
        assertThat(jdbc.update(sql, anaId, categoria, "x".repeat(100), "x".repeat(100))).isEqualTo(1);
    }

    private DatosServicio datos(int tarifa, int intensidad) {
        return new DatosServicio(tutorias.getId(), "Título", "Descripción", tarifa, intensidad, null, null, List.of());
    }
}
