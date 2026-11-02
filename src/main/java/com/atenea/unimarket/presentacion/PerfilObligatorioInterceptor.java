package com.atenea.unimarket.presentacion;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.servlet.HandlerInterceptor;

import com.atenea.unimarket.negocio.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Sends a signed-in Google identity that has not completed its profile to the profile form,
 * whatever page it asks for. Registration only happens when that form is saved.
 */
public class PerfilObligatorioInterceptor implements HandlerInterceptor {

    static final String FORMULARIO = "/perfil/nuevo";

    private final UsuarioService usuarios;

    public PerfilObligatorioInterceptor(UsuarioService usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacion != null && autenticacion.getPrincipal() instanceof OidcUser oidc
                && !usuarios.estaRegistrado(oidc.getSubject())) {
            response.sendRedirect(request.getContextPath() + FORMULARIO);
            return false;
        }
        return true;
    }
}
