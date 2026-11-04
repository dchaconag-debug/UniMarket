package com.atenea.unimarket.datos.modelo;

/** What a notification is about. Must match the CHECK of the {@code notificacion} table. */
public enum TipoNotificacion {
    SOLICITUD_RECIBIDA,
    CONTRAPROPUESTA_RECIBIDA,
    ACUERDO_CONFIRMADO,
    SOLICITUD_RECHAZADA
}
