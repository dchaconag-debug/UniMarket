package com.atenea.unimarket.presentacion.controller;

import java.text.NumberFormat;
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

import com.atenea.unimarket.datos.modelo.Categoria;
import com.atenea.unimarket.datos.modelo.DiaSemana;
import com.atenea.unimarket.datos.modelo.Servicio;
import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.negocio.CategoriaNoEncontradaException;
import com.atenea.unimarket.negocio.ServicioService;
import com.atenea.unimarket.negocio.UsuarioService;
import com.atenea.unimarket.presentacion.formulario.FranjaFormulario;
import com.atenea.unimarket.presentacion.formulario.ServicioFormulario;

@Controller
public class ServicioController {

    private static final Locale COLOMBIA = Locale.forLanguageTag("es-CO");
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", COLOMBIA);

    private final ServicioService servicios;
    private final UsuarioService usuarios;

    public ServicioController(ServicioService servicios, UsuarioService usuarios) {
        this.servicios = servicios;
        this.usuarios = usuarios;
    }

    @ModelAttribute("dias")
    DiaSemana[] dias() {
        return DiaSemana.values();
    }

    @GetMapping("/servicios/nuevo")
    public String formulario(Model model) {
        model.addAttribute("servicio", new ServicioFormulario());
        model.addAttribute("categorias", servicios.categorias());
        return "servicio/formulario";
    }

    /** Adds or removes a slot row without JavaScript; the form is re-rendered without validating. */
    @PostMapping(path = "/servicios/nuevo", params = {"accion"})
    public String editarFranjas(@ModelAttribute("servicio") ServicioFormulario formulario,
            BindingResult erroresDeConversion, @RequestParam String accion, Model model) {
        FranjaFormulario.aplicarAccion(formulario.getFranjas(), accion);
        model.addAttribute("categorias", servicios.categorias());
        return "servicio/formulario";
    }

    @PostMapping("/servicios/nuevo")
    public String publicar(@AuthenticationPrincipal OidcUser oidc,
            @Validated @ModelAttribute("servicio") ServicioFormulario formulario, BindingResult errores,
            RedirectAttributes redireccion, Model model) {
        if (formulario.getCategoriaId() != null && servicios.buscarCategoria(formulario.getCategoriaId()).isEmpty()) {
            errores.rejectValue("categoriaId", "categoria.inexistente", "Selecciona una categoría de la lista.");
        }
        if (errores.hasErrors()) {
            model.addAttribute("categorias", servicios.categorias());
            return "servicio/formulario";
        }
        Servicio servicio;
        try {
            servicio = servicios.publicar(usuarioActual(oidc).getId(), formulario.aDatos());
        } catch (CategoriaNoEncontradaException noExiste) {
            errores.rejectValue("categoriaId", "categoria.inexistente", "Selecciona una categoría de la lista.");
            model.addAttribute("categorias", servicios.categorias());
            return "servicio/formulario";
        }
        redireccion.addFlashAttribute("mensaje", "Tu servicio fue publicado.");
        return "redirect:/servicios/" + servicio.getId();
    }

    @GetMapping("/servicios/{id}")
    public String ver(@PathVariable Integer id, Model model) {
        Servicio servicio = servicios.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ZonedDateTime publicacion = servicio.getFechaPublicacion().atZone(PerfilController.ZONA);
        model.addAttribute("servicio", servicio);
        model.addAttribute("tarifa", pesos(servicio.getTarifaPorHora()));
        model.addAttribute("fechaPublicacionIso", publicacion.toOffsetDateTime().toString());
        model.addAttribute("fechaPublicacionTexto", FECHA.format(publicacion));
        return "servicio/ver";
    }

    @GetMapping("/categorias")
    public String categorias(Model model) {
        model.addAttribute("categorias", servicios.categorias());
        return "categoria/lista";
    }

    @GetMapping("/categorias/{id}")
    public String categoria(@PathVariable Integer id, Model model) {
        Categoria categoria = servicios.buscarCategoria(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("categoria", categoria);
        model.addAttribute("servicios", servicios.disponiblesEnCategoria(id));
        model.addAttribute("formato", new FormatoPesos());
        return "categoria/ver";
    }

    static String pesos(int valor) {
        NumberFormat formato = NumberFormat.getIntegerInstance(COLOMBIA);
        return "$" + formato.format(valor) + " COP";
    }

    /** Lets templates format a list of rates without a helper per item. */
    public static final class FormatoPesos {
        public String pesos(int valor) {
            return ServicioController.pesos(valor);
        }
    }

    private Usuario usuarioActual(OidcUser oidc) {
        return usuarios.buscarPorGoogleSub(oidc.getSubject())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
    }
}
