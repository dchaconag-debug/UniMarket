package com.atenea.unimarket.negocio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.datos.repositorio.UsuarioRepository;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.datasource.url=jdbc:sqlite:target/usuario-service-test.db")
class UsuarioServiceTests {

    @Autowired
    UsuarioService servicio;

    @Autowired
    UsuarioRepository usuarios;

    @BeforeEach
    void limpiar() {
        usuarios.deleteAll();
    }

    @Test
    void primerInicioDeSesionCreaElUsuario() {
        Usuario usuario = servicio.registrarSiNoExiste("sub-1", "Ana", "ana@unal.edu.co");

        Usuario guardado = usuarios.findByGoogleSub("sub-1").orElseThrow();
        assertThat(guardado.getId()).isEqualTo(usuario.getId());
        assertThat(guardado.getNombre()).isEqualTo("Ana");
        assertThat(guardado.getCorreoInstitucional()).isEqualTo("ana@unal.edu.co");
        assertThat(guardado.getFechaCreacion()).isNotNull();
    }

    @Test
    void inicioDeSesionPosteriorReutilizaElUsuario() {
        Usuario primero = servicio.registrarSiNoExiste("sub-1", "Ana", "ana@unal.edu.co");
        Usuario segundo = servicio.registrarSiNoExiste("sub-1", "Ana María", "ana@unal.edu.co");

        assertThat(segundo.getId()).isEqualTo(primero.getId());
        assertThat(usuarios.count()).isEqualTo(1);
    }

    @Test
    void inicioDeSesionConcurrenteReutilizaElUsuarioQueGanoLaInsercion() {
        UsuarioRepository repositorio = mock(UsuarioRepository.class);
        Usuario existente = new Usuario("sub-1", "Ana", "ana@unal.edu.co", Instant.EPOCH);
        when(repositorio.findByGoogleSub("sub-1")).thenReturn(Optional.empty(), Optional.of(existente));
        when(repositorio.saveAndFlush(any())).thenThrow(new DataIntegrityViolationException("google_sub"));

        Usuario usuario = new UsuarioService(repositorio, Clock.systemUTC())
                .registrarSiNoExiste("sub-1", "Ana", "ana@unal.edu.co");

        assertThat(usuario).isSameAs(existente);
    }
}
