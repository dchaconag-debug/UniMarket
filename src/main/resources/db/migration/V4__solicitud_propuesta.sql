CREATE TABLE solicitud_compra (
    id INTEGER PRIMARY KEY,
    cliente_id INTEGER NOT NULL REFERENCES usuario (id),
    servicio_id INTEGER NOT NULL REFERENCES servicio (id),
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_ultima_actividad TIMESTAMP NOT NULL,
    estado VARCHAR(20) NOT NULL
        CHECK (estado IN ('NEGOCIANDO', 'ACTIVA', 'CERRADA', 'CANCELADA'))
);

CREATE INDEX solicitud_servicio_estado ON solicitud_compra (servicio_id, estado);
CREATE INDEX solicitud_cliente ON solicitud_compra (cliente_id, estado);

CREATE TABLE propuesta (
    id INTEGER PRIMARY KEY,
    solicitud_id INTEGER NOT NULL REFERENCES solicitud_compra (id),
    autor_id INTEGER NOT NULL REFERENCES usuario (id),
    -- Whole Colombian pesos per hour.
    tarifa_por_hora INTEGER NOT NULL CHECK (tarifa_por_hora > 0),
    -- Hours per week. Always filled: a client proposal copies the current value.
    intensidad_horaria INTEGER NOT NULL CHECK (intensidad_horaria BETWEEN 1 AND 168),
    numero_ronda INTEGER NOT NULL CHECK (numero_ronda >= 1),
    fecha_envio TIMESTAMP NOT NULL,
    UNIQUE (solicitud_id, numero_ronda)
);