package com.atenea.unimarket.presentacion.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atenea.unimarket.datos.modelo.DiaSemana;
import com.atenea.unimarket.datos.modelo.Perfil;
import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.negocio.PerfilService;
import com.atenea.unimarket.negocio.PerfilYaExisteException;
import com.atenea.unimarket.negocio.ServicioService;
import com.atenea.unimarket.negocio.UsuarioService;
import com.atenea.unimarket.presentacion.formulario.FranjaFormulario;
import com.atenea.unimarket.presentacion.formulario.PerfilFormulario;

@Controller
public class PerfilController {

    static final ZoneId ZONA = ZoneId.of("America/Bogota");
    private static final DateTimeFormatter FECHA =
            DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, HH:mm", Locale.forLanguageTag("es-CO"));

    private final UsuarioService usuarios;
    private final PerfilService perfiles;
    private final ServicioService servicios;

    public PerfilController(UsuarioService usuarios, PerfilService perfiles, ServicioService servicios) {
        this.usuarios = usuarios;
        this.perfiles = perfiles;
        this.servicios = servicios;
    }

    @ModelAttribute("dias")
    DiaSemana[] dias() {
        return DiaSemana.values();
    }

    @GetMapping("/perfil")
    public String miPerfil(@AuthenticationPrincipal OidcUser oidc) {
        return "redirect:/perfiles/" + usuarioActual(oidc).getId();
    }

    @GetMapping("/perfil/nuevo")
    public String formularioNuevo(@AuthenticationPrincipal OidcUser oidc, Model model) {
        if (usuarios.estaRegistrado(oidc.getSubject())) {
            return "redirect:/perfil";
        }
        PerfilFormulario formulario = new PerfilFormulario();
        formulario.setNombre(oidc.getFullName());
        model.addAttribute("perfil", formulario);
        model.addAttribute("correo", oidc.getEmail());
        return "perfil/formulario";
    }

    /** Adds or removes a slot row without JavaScript; the form is re-rendered without validating. */
    @PostMapping(path = "/perfil/nuevo", params = {"accion"})
    public String editarFranjas(@AuthenticationPrincipal OidcUser oidc,
            @ModelAttribute("perfil") PerfilFormulario formulario, BindingResult erroresDeConversion,
            @RequestParam String accion, Model model) {
        if (usuarios.estaRegistrado(oidc.getSubject())) {
            return "redirect:/perfil";
        }
        model.addAttribute("correo", oidc.getEmail());
        FranjaFormulario.aplicarAccion(formulario.getFranjas(), accion);
        return "perfil/formulario";
    }

    @PostMapping("/perfil/nuevo")
    public String registrar(@AuthenticationPrincipal OidcUser oidc,
            @Validated @ModelAttribute("perfil") PerfilFormulario formulario, BindingResult errores,
            RedirectAttributes redireccion, Model model) {
        if (usuarios.estaRegistrado(oidc.getSubject())) {
            return "redirect:/perfil";
        }
        if (errores.hasErrors()) {
            model.addAttribute("correo", oidc.getEmail());
            return "perfil/formulario";
        }
        Perfil perfil;
        try {
            perfil = perfiles.registrar(oidc.getSubject(), oidc.getEmail(), formulario.getNombre(),
                    formulario.getTelefonoContacto(), formulario.horarios());
        } catch (PerfilYaExisteException yaExiste) {
            return "redirect:/perfil";
        }
        redireccion.addFlashAttribute("mensaje", "Tu perfil fue creado.");
        return "redirect:/perfiles/" + perfil.getUsuario().getId();
    }

    @GetMapping("/perfiles/{usuarioId}")
    public String ver(@PathVariable Integer usuarioId, @AuthenticationPrincipal OidcUser oidc, Model model) {
        Perfil perfil = perfiles.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("perfil", perfil);
        model.addAttribute("servicios", servicios.disponiblesDeOfertador(usuarioId));
        model.addAttribute("formato", new ServicioController.FormatoPesos());
        model.addAttribute("propio", perfil.getUsuario().getId().equals(usuarioActual(oidc).getId()));
        ZonedDateTime actualizacion = perfil.getFechaActualizacion().atZone(ZONA);
        model.addAttribute("fechaActualizacionIso", actualizacion.toOffsetDateTime().toString());
        model.addAttribute("fechaActualizacionTexto", FECHA.format(actualizacion));
        return "perfil/ver";
    }

    private Usuario usuarioActual(OidcUser oidc) {
        return usuarios.buscarPorGoogleSub(oidc.getSubject())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
    }
}
