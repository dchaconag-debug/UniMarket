CREATE TABLE categoria (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- Fixed list until an admin role exists to manage categories.
INSERT INTO categoria (nombre) VALUES
    ('Tutorías'),
    ('Clases particulares'),
    ('Asesoría de tesis y trabajos'),
    ('Diseño'),
    ('Escritura');

-- SQLite ignores VARCHAR(n) lengths, so text limits are explicit CHECKs.
CREATE TABLE servicio (
    id INTEGER PRIMARY KEY,
    ofertador_id INTEGER NOT NULL REFERENCES usuario (id),
    categoria_id INTEGER NOT NULL REFERENCES categoria (id),
    titulo VARCHAR(100) NOT NULL CHECK (length(titulo) BETWEEN 1 AND 100),
    descripcion TEXT NOT NULL CHECK (length(descripcion) BETWEEN 1 AND 5000),
    -- Whole Colombian pesos per hour; no decimals to avoid floating-point storage.
    tarifa_por_hora INTEGER NOT NULL CHECK (tarifa_por_hora > 0),
    -- Hours per week.
    intensidad_horaria INTEGER NOT NULL CHECK (intensidad_horaria BETWEEN 1 AND 168),
    experiencia TEXT CHECK (experiencia IS NULL OR length(experiencia) <= 5000),
    especialidad VARCHAR(100) CHECK (especialidad IS NULL OR length(especialidad) <= 100),
    -- JSON array of weekly slots, same format as perfil.horarios_disponibilidad.
    horarios_disponibilidad TEXT
        CHECK (horarios_disponibilidad IS NULL
            OR (json_valid(horarios_disponibilidad) AND json_type(horarios_disponibilidad) = 'array')),
    fecha_publicacion TIMESTAMP NOT NULL,
    estado VARCHAR(20) NOT NULL
        CHECK (estado IN ('DISPONIBLE', 'ACTIVO', 'CERRADO', 'CANCELADO'))
);

CREATE INDEX servicio_catalogo ON servicio (estado, categoria_id);
CREATE INDEX servicio_ofertador ON servicio (ofertador_id);
