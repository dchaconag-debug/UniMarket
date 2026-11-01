# Fuentes y revisión de extracción

## Archivos conservados

| Fuente | Original | Texto por página PDF |
| --- | --- | --- |
| E2, especificación de requisitos | [Entrega_2_Atenea.pdf](entrega-2.pdf), 37 páginas | [Extracción E2](entrega-2-texto.md) |
| E3, diapositivas de diseño | [Diapositivas Entrega 3.pdf](entrega-3.pdf), 13 páginas | [Extracción E3](entrega-3-texto.md) |

Los PDFs son copias de los adjuntos. El texto se obtuvo con `pdftotext -layout` y se separó por página física. Conserva errores y repeticiones de la fuente; el contexto vigente está en [requisitos](../requisitos.md) y [diseño](../diseno.md).

## Cobertura visual

Se revisaron las 13 diapositivas de E3 y las 37 páginas de E2 renderizadas. En E2 se inspeccionó la distribución mediante hojas de contacto, además de la lectura del texto extraído; la última captura se abrió individualmente. Los diagramas E3 pp. 9-13 se abrieron individualmente a mayor resolución y se transcribieron a [diseño](../diseno.md).

- E2 pp. 3-22: las tablas separan precondiciones, garantías, flujo y extensiones. El texto lineal no conserva bien esa separación; los requisitos sintetizados recuperan su función.
- E2 p. 24: diagrama de casos de uso con Usuario, Cliente, Ofertador y Denunciante. Incluye extensiones por servicio cerrado, incumplimiento y perfil no verificado.
- E2 pp. 25-32: 21 historias de usuario con criterios de aceptación. Los números impresos aquí son 23-30.
- E2 pp. 33-34: acta de entrevista. Las dos columnas separan declaraciones del entrevistado de interpretaciones del equipo.
- E2 pp. 35-37: capturas históricas del backlog, con historias en Todo y sin elementos visibles en In Progress o Done. No aportan nuevos criterios de aceptación. No se usaron para inferir progreso actual.
- E3 pp. 2-4: columnas de contexto y estados, prioridades, capas y comunicación externa. La disposición visual confirma que estados de servicio y solicitud son listas distintas.
- E3 pp. 5-8: identificación de sustantivos y verbos con resaltados. Los nombres de clases y métodos son borradores. La clasificación resultante está resumida en diseño.
- E3 pp. 9-13: modelo de dominio, secuencia de aceptación, secuencia de publicación, clases de diseño y persistencia. La extracción automática omite el contenido de estas imágenes; se conservaron [las cinco imágenes](../diagramas/) y se documentó su contenido manualmente.

La revisión visual no convierte detalles ausentes o símbolos sin leyenda en restricciones confirmadas. Las ambigüedades del esquema se indican junto a las reglas de diseño correspondientes.

## Contexto de entrevista

E2 pp. 33-34, acta Atenea n.º 2, 6 de octubre de 2026, duración 9:55. Entrevistado: Julian Andres Foglia Wilches, tutor de cálculo diferencial, aritmética, álgebra, termodinámica y fluidos.

Hallazgos declarados: la dificultad de la materia influye en la tarifa; las cancelaciones cercanas a la cita ocasionan pérdida de tiempo y transporte; el tutor fija tarifa al inicio y negocia según lo que plantea el estudiante. El equipo interpreta que calificaciones y reputación pueden ayudar al cliente a decidir. Esa interpretación no implica que se haya especificado un algoritmo de ranking.

Preguntas pendientes de la entrevista: factores para medir dificultad y fijar tarifa, rangos por área, condiciones de descuento, experiencia con reclamos o penalizaciones y proporción presencial frente a virtual. No se inventaron respuestas.

El documento enlaza un [tablero de GitHub Projects](https://github.com/users/nauj2024/projects/1/views/1). Se conserva como referencia de la fuente; no se consultó su estado en línea.

La portada de E2 dice septiembre de 2026 y el anexo contiene una entrevista de octubre. Se conservan ambas fechas sin deducir una fecha de publicación. E3 solo indica 2026.
