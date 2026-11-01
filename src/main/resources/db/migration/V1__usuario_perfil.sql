CREATE TABLE usuario (
    id INTEGER PRIMARY KEY,
    google_sub VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    correo_institucional VARCHAR(150) NOT NULL UNIQUE,
    fecha_creacion TIMESTAMP NOT NULL
);

CREATE TABLE perfil (
    id INTEGER PRIMARY KEY,
    usuario_id INTEGER NOT NULL UNIQUE REFERENCES usuario (id),
    telefono_contacto VARCHAR(20),
    -- JSON array of weekly slots: [{"dia":"LUNES","inicio":"08:00","fin":"10:00"}]
    horarios_disponibilidad TEXT
        CHECK (horarios_disponibilidad IS NULL
            OR (json_valid(horarios_disponibilidad) AND json_type(horarios_disponibilidad) = 'array')),
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP NOT NULL
);
