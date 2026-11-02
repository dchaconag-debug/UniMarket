# Base del proyecto UniMarket

Base vigente de producto y diseño del equipo Atenea, Ingeniería de Software I, Universidad Nacional de Colombia, 2026.

## Lectura por tarea

| Tarea | Requisitos | Diseño |
| --- | --- | --- |
| Compra y negociación | [CU-01](requisitos.md#cu-01-comprar--negociar-servicio) | [Aceptación](diseno.md#secuencia-de-aceptación-de-propuesta), [estados](diseno.md#estados) |
| Publicación y edición | [CU-02](requisitos.md#cu-02-ofertar-y-editar-servicio) | [Publicación](diseno.md#secuencia-de-publicación) |
| Verificación y perfil | [CU-03](requisitos.md#cu-03-verificar-perfil), [CU-04](requisitos.md#cu-04-crear-y-actualizar-perfil) | [Entidades](diseno.md#entidades-y-asociaciones) |
| Catálogo y guardados | [CU-05](requisitos.md#cu-05-navegar-servicios-disponibles) | [Estados](diseno.md#estados) |
| Reseñas y denuncias | [CU-06](requisitos.md#cu-06-dejar-reseña), [CU-07](requisitos.md#cu-07-dejar-denuncia) | [Estados](diseno.md#estados) |
| Arquitectura y almacenamiento | Casos afectados en [requisitos](requisitos.md) | [Arquitectura](diseno.md#arquitectura), [interfaces](diseno.md#interfaces-de-diseño), [persistencia](diseno.md#persistencia) |

Leer las secciones de la tarea y sus enlaces. Cada sección reúne las reglas vigentes y los detalles aún por definir. Cuando un detalle afecte el comportamiento que se implementará, obtener la decisión del equipo y actualizar esa sección; continuar el trabajo independiente de esa decisión.

## Uso de esta base

[Requisitos](requisitos.md) y [diseño](diseno.md) forman una sola especificación. La base incorpora las entregas 2 y 3, con Entrega 3 como fuente de verdad. Los [PDF y extracciones](fuentes/README.md) se conservan para consultar evidencia, diagramas y antecedentes.

Los documentos describen el producto previsto. Para determinar qué está implementado, consultar el código y sus pruebas. Los nombres de clases y métodos son borradores del diseño.

Las referencias `E2 p. N` y `E3 p. N` usan la página física del PDF desde 1, no la numeración impresa.
