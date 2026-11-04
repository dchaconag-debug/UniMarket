package com.atenea.unimarket.negocio;

import java.time.Clock;
import java.time.Instant;

import org.springframework.stereotype.Component;

import com.atenea.unimarket.datos.modelo.Notificacion;
import com.atenea.unimarket.datos.modelo.SolicitudCompra;
import com.atenea.unimarket.datos.modelo.TipoNotificacion;
import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.datos.repositorio.NotificacionRepository;

/**
 * Stores each notification so the user finds it in the inbox. It joins the caller's
 * transaction, so a notification exists only if the change it reports was saved.
 */
@Component
class NotificadorPersistente implements Notificador {

    private final NotificacionRepository notificaciones;
    private final Clock reloj;

    NotificadorPersistente(NotificacionRepository notificaciones, Clock reloj) {
        this.notificaciones = notificaciones;
        this.reloj = reloj;
    }

    @Override
    public void notificar(Usuario destinatario, SolicitudCompra solicitud, TipoNotificacion tipo, String mensaje) {
        notificaciones.save(new Notificacion(destinatario, solicitud, tipo, mensaje, Instant.now(reloj)));
    }
}
