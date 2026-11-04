package com.atenea.unimarket.negocio;

import com.atenea.unimarket.datos.modelo.SolicitudCompra;
import com.atenea.unimarket.datos.modelo.TipoNotificacion;
import com.atenea.unimarket.datos.modelo.Usuario;

/** Tells a user about something that happened in a purchase request. */
public interface Notificador {

    void notificar(Usuario destinatario, SolicitudCompra solicitud, TipoNotificacion tipo, String mensaje);
}
