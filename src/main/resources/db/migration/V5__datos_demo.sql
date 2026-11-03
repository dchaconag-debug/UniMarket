-- Datos de demostración para el catálogo y la sustentación.
-- Los usuarios llevan google_sub ficticio: no pueden iniciar sesión.
INSERT INTO usuario (google_sub, nombre, correo_institucional, fecha_creacion) VALUES
    ('demo-ana',    'Ana Rodríguez', 'ana.demo@unimarket.invalid',    '2026-09-01 08:00:00'),
    ('demo-carlos', 'Carlos Gómez',  'carlos.demo@unimarket.invalid', '2026-09-01 08:05:00'),
    ('demo-laura',  'Laura Pérez',   'laura.demo@unimarket.invalid',  '2026-09-01 08:10:00');

INSERT INTO perfil (usuario_id, telefono_contacto, horarios_disponibilidad, fecha_creacion, fecha_actualizacion)
SELECT id, '3001234567', '[{"dia":"LUNES","inicio":"14:00","fin":"18:00"}]',
       '2026-09-01 08:00:00', '2026-09-01 08:00:00'
FROM usuario WHERE google_sub LIKE 'demo-%';

WITH datos (sub, categoria, titulo, descripcion, tarifa, intensidad, especialidad, publicado, estado) AS (VALUES
    ('demo-ana',    'Tutorías',                     'Tutoría de cálculo diferencial',        'Límites, derivadas y aplicaciones con ejercicios guiados.',      35000, 4, 'Matemáticas',    '2026-10-01 10:00:00', 'DISPONIBLE'),
    ('demo-ana',    'Clases particulares',          'Clases de inglés conversacional',       'Práctica oral enfocada en exposiciones y entrevistas.',          28000, 3, 'Idiomas',        '2026-10-05 09:00:00', 'DISPONIBLE'),
    ('demo-carlos', 'Asesoría de tesis y trabajos', 'Asesoría metodológica para tesis',      'Diseño de la metodología y revisión del marco teórico.',         45000, 2, 'Metodología',    '2026-10-10 15:00:00', 'DISPONIBLE'),
    ('demo-carlos', 'Diseño',                       'Diseño de pósters académicos',          'Pósters claros y listos para impresión en formato A1.',          40000, 2, 'Diseño gráfico', '2026-10-15 11:00:00', 'DISPONIBLE'),
    ('demo-laura',  'Escritura',                    'Corrección de estilo y ortografía',     'Revisión de ensayos, informes y trabajos de grado.',             22000, 5, NULL,             '2026-10-20 16:00:00', 'DISPONIBLE'),
    ('demo-laura',  'Tutorías',                     'Tutoría de álgebra lineal',             'Matrices, vectores y espacios vectoriales.',                     30000, 3, 'Matemáticas',    '2026-10-25 08:00:00', 'ACTIVO'),
    ('demo-carlos', 'Escritura',                    'Redacción de informes técnicos',        'Estructura y redacción de informes de laboratorio.',             38000, 4, NULL,             '2026-09-20 10:00:00', 'CERRADO'),
    ('demo-ana',    'Diseño',                       'Identidad visual para emprendimientos', 'Logo y paleta de color para proyectos universitarios.',          50000, 6, 'Diseño gráfico', '2026-09-25 10:00:00', 'CANCELADO'))
INSERT INTO servicio (ofertador_id, categoria_id, titulo, descripcion, tarifa_por_hora, intensidad_horaria,
                      especialidad, horarios_disponibilidad, fecha_publicacion, estado)
SELECT u.id, c.id, d.titulo, d.descripcion, d.tarifa, d.intensidad, d.especialidad, '[]', d.publicado, d.estado
FROM datos d
JOIN usuario u ON u.google_sub = d.sub
JOIN categoria c ON c.nombre = d.categoria;