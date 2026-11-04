-- Notificaciones persistidas: lo que cada usuario ve en su bandeja.
-- Los tipos cubren todo el flujo de compra para que H02 no tenga que reconstruir la tabla:
-- SQLite no permite modificar un CHECK sin recrearla.
CREATE TABLE notificacion (
    id INTEGER PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuario (id),
    solicitud_id INTEGER NOT NULL REFERENCES solicitud_compra (id),
    mensaje TEXT NOT NULL CHECK (length(mensaje) BETWEEN 1 AND 500),
    tipo VARCHAR(30) NOT NULL
        CHECK (tipo IN ('SOLICITUD_RECIBIDA', 'CONTRAPROPUESTA_RECIBIDA', 'ACUERDO_CONFIRMADO', 'SOLICITUD_RECHAZADA')),
    fecha_creacion TIMESTAMP NOT NULL
);

CREATE INDEX notificacion_usuario ON notificacion (usuario_id, fecha_creacion);
