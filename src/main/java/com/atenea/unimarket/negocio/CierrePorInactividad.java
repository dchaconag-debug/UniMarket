package com.atenea.unimarket.negocio;

import java.time.Clock;
import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** revisa cada hora las negociaciones sin respuesta del ofertador. */
@Component
class CierrePorInactividad {

    private final SolicitudCompraService solicitudes;
    private final Clock reloj;

    CierrePorInactividad(SolicitudCompraService solicitudes, Clock reloj) {
        this.solicitudes = solicitudes;
        this.reloj = reloj;
    }

    @Scheduled(cron = "0 0 * * * *")
    void cerrarInactivas() {
        solicitudes.cerrarInactivas(Instant.now(reloj));
    }
}
