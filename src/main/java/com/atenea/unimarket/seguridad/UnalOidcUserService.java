package com.atenea.unimarket.seguridad;

import java.util.Locale;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.atenea.unimarket.negocio.UsuarioService;

public class UnalOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OAuth2UserService<OidcUserRequest, OidcUser> delegate;
    private final UsuarioService usuarios;

    public UnalOidcUserService(OAuth2UserService<OidcUserRequest, OidcUser> delegate, UsuarioService usuarios) {
        this.delegate = delegate;
        this.usuarios = usuarios;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest request) {
        OidcUser user = delegate.loadUser(request);
        // Spring Security validates the ID token before calling this service.
        OidcIdToken token = user.getIdToken();
        String email = token.getEmail();
        boolean unalEmail = email != null && email.matches("(?i)[^@\\s]+@unal\\.edu\\.co");

        if (!Boolean.TRUE.equals(token.getEmailVerified())
                || !unalEmail
                || !"unal.edu.co".equals(token.getClaimAsString("hd"))) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("unal_account_required"),
                    "Debes ingresar con una cuenta institucional de la UNAL.");
        }

        String nombre = user.getFullName() != null ? user.getFullName() : email;
        usuarios.registrarSiNoExiste(token.getSubject(), nombre, email.toLowerCase(Locale.ROOT));
        return user;
    }
}
