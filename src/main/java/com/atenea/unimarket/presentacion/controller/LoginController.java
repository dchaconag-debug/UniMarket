package com.atenea.unimarket.presentacion.controller;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.negocio.UsuarioService;

@Controller
public class LoginController {

    private final ObjectProvider<ClientRegistrationRepository> registrations;
    private final UsuarioService usuarios;

    public LoginController(ObjectProvider<ClientRegistrationRepository> registrations, UsuarioService usuarios) {
        this.registrations = registrations;
        this.usuarios = usuarios;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("googleConfigured", registrations.getIfAvailable() != null);
        return "login";
    }

    @GetMapping("/")
    public String inicio(@AuthenticationPrincipal OidcUser user, Model model) {
        // PerfilObligatorioInterceptor guarantees a registered user here.
        Usuario usuario = usuarios.buscarPorGoogleSub(user.getSubject()).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "inicio";
    }
}
