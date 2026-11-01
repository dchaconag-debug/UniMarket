package com.atenea.unimarket.negocio;

import java.time.Clock;
import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.datos.repositorio.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarios;
    private final Clock reloj;

    public UsuarioService(UsuarioRepository usuarios, Clock reloj) {
        this.usuarios = usuarios;
        this.reloj = reloj;
    }

    /**
     * Returns the account for this Google identity, creating it on the first login.
     * Not transactional on purpose: if a concurrent first login inserts the same
     * {@code google_sub} first, the failed insert rolls back alone and the lookup
     * below sees the committed row.
     */
    public Usuario registrarSiNoExiste(String googleSub, String nombre, String correoInstitucional) {
        return usuarios.findByGoogleSub(googleSub).orElseGet(() -> {
            try {
                return usuarios.saveAndFlush(
                        new Usuario(googleSub, nombre, correoInstitucional, Instant.now(reloj)));
            } catch (DataIntegrityViolationException carrera) {
                return usuarios.findByGoogleSub(googleSub).orElseThrow(() -> carrera);
            }
        });
    }
}
