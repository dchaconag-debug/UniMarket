package com.atenea.unimarket.negocio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.datos.repositorio.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarios;

    public UsuarioService(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    public Optional<Usuario> buscarPorGoogleSub(String googleSub) {
        return usuarios.findByGoogleSub(googleSub);
    }

    /** A Google identity is registered once it has completed its profile; see {@link PerfilService#registrar}. */
    public boolean estaRegistrado(String googleSub) {
        return usuarios.existsByGoogleSub(googleSub);
    }
}
