package com.atenea.unimarket.dev;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@ControllerAdvice
@Profile("dev")
public class DevLoginController {

    public record Account(String subject, String name, String email) {}

    private static final List<Account> ACCOUNTS = List.of(
            new Account("demo-ana", "Ana Rodríguez", "ana.demo@unal.edu.co"),
            new Account("demo-carlos", "Carlos Gómez", "carlos.demo@unal.edu.co"),
            new Account("demo-laura", "Laura Pérez", "laura.demo@unal.edu.co"),
            new Account("dev-nuevo", "Nuevo estudiante", "nuevo.dev@unal.edu.co"));

    private final SecurityContextRepository repository;

    public DevLoginController(SecurityContextRepository repository) {
        this.repository = repository;
    }

    @ModelAttribute("devAccounts")
    public List<Account> accounts() {
        return ACCOUNTS;
    }

    @PostMapping("/dev/login")
    public String login(@RequestParam String subject, HttpServletRequest request, HttpServletResponse response) {
        Account account = ACCOUNTS.stream().filter(candidate -> candidate.subject().equals(subject))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Instant now = Instant.now();
        OidcIdToken token = new OidcIdToken("dev-only", now, now.plusSeconds(3600), Map.of(
                "sub", account.subject(), "name", account.name(), "email", account.email(),
                "email_verified", true, "hd", "unal.edu.co"));
        DefaultOidcUser user = new DefaultOidcUser(List.of(), token);
        var authentication = new OAuth2AuthenticationToken(user, user.getAuthorities(), "dev");
        if (request.getSession(false) != null) {
            request.changeSessionId();
        }
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        repository.saveContext(context, request, response);
        return "redirect:/";
    }
}
