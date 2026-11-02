# Entrega 3: extracción de texto
Texto automático con distribución de página conservada. La especificación vigente está en [requisitos](../requisitos.md) y [diseño](../diseno.md). Los diagramas y capturas deben consultarse en el PDF y en las notas de revisión visual.

## Página PDF 1
```text
                                          Sofía Franco
                                          Mariana García
                                          Julián Gutiérrez
                                          David Chacón
                                          Juan Sebastián Camacho

Atenea
                                          Camilo Esteban Páez




Entrega 3


2026   Universidad Nacional de Colombia         Ingeniería de Software I
```

## Página PDF 2
```text
Contexto del
Proyecto                                     Para servicio (EstadoServicio):

                                               ●   Disponible: No tiene ninguna solicitud Activa (puede tener
                                                   solicitudes en negociación), fue publicado y es visible para los
                                                   clientes
UniMarket consiste en una plataforma de        ●   Activo: Ya se cerró la negociación de una solicitud (estado Activa)
compra y venta de servicios ofrecidos por          y no se ha cumplido el plazo
la comunidad universitaria, tales como:        ●   Cerrado: Ya se cumplió el plazo o se entregó lo acordado
   ●  Tutorías y clases privadas               ●   Cancelado: No se cumplió con lo acordado pero no se continuará
   ●  Asesorías en tesis y trabajos                con la entrega del servicio
   ●  Servicios y entregas de proyectos
      (de diseño, escritura, etc.)           Para una solicitud (EstadoSolicitud):

Los casos de uso principales consisten en:     ●   Negociando: Cliente demostró interés y comenzó negociación
  ●   Comprar, ofertar, navegar y              ●   Activa: Ya se realizó un acuerdo (propuesta aceptada) y comenzó
      actualizar servicios                         el plazo de entrega
  ●   Veriﬁcar, crear y actualizar perﬁles     ●   Cerrada: Ya se cumplió con la entrega del servicio. Se permite
      de usuario                                   realizar reseñas y denuncias.
  ●   Reseñar y denunciar servicios una        ●   Cancelada: No se continuó con la solicitud. No se marca espacio
      vez realizados                               para denuncio o reseña (la negociación falló)



01
                                                                                                     Atenea
```

## Página PDF 3
```text
02 Requerimientos
  Casos de Uso de Mayor prioridad

  1    CU-01: Comprar / Negociar Servicio

  ●    Nucleo del negocio: Negociación de tarifa e
       intensidad horaria entre cliente y ofertador
  ●    Maneja los estados de solicitud y servicio y
       es la base de CU-06 y CU-07




   1   CU-02: Ofertar y Editar Servicio
  ●    Deﬁne el servicio (categoria, tarifa,
       intensidad) que CU-01 negocia. Sin oferta no
       hay catalogo
  ●    Valida la información mínima obligatoria y la
       condición de perﬁl veriﬁcado institucional
```

## Página PDF 4
```text
03 Arquitectura
                                              Presentación                                Cerrada
                                              Maneja la interfaz, interactúa con el usuario y
                                              recibe sus peticiones.

                                              Ej: Usuario decide proponer una tarifa (CU-01).
                                                                                                     Externos    Cerrada
Estilo y despliegue                                                                                  Administra la
                                              Negocio                                                comunicación con
                                                                                          Cerrada
  ●   Capas: Evita el antipatron                                                                     plataformas.
                                              Concentra todas las reglas centrales y la lógica
      “Big Ball of Mud”                       de funcionamiento del marketplace.
                                                                                                     Ej:Se conecta a un
                                                                                                     proveedor para el
  ●   Monolito: Una única unidad de           Ej: Ejecuta las validaciones del CU-02
                                                                                                     envío del correo o SMS
      despliegue con BD conectada                                                                    con el código de
                                                                                                     veriﬁcación (CU-03)
                                              Servicios Compartidos                      Abierta
El Principio de Aislamiento:
                                              Proporciona módulos de utilidades transversales que
                                              apoyan al resto del sistema.
  ●   Las capas Cerradas obligan a que la
                                              Ej: Gestiona el registro del historial con fechas,
      petición atraviese el nivel inferior,
                                              auditoría y utilidades de validación que pueden ser
      protegiendo las reglas de negocio.      usadas directamente por la capa de Negocio

  ●   Las capas Abiertas previenen el
      Architecture Sinkhole, permitiendo un   Datos                                       Cerrada
                                              Oculta la complejidad de la base de datos y gestiona
      ﬂujo directo cuando no se requieren     el almacenamiento seguro de la información
      intermediarios de utilidad.
                                              Ej: Implementa repositorios para guardar o consultar
                                              los datos en la base de datos relacional en archivo
```

## Página PDF 5
```text
04 Identiﬁcación de Objetos
                                                                                         Palabra: Sustantivo en el contexto
                                                                                                            Palabra: Verbo
 Caso 1: Comprar un servicio                                                     Solo se marca cada palabra en su primera
                                                                                                                  aparición

  1.   El cliente selecciona un servicio publicado por un ofertador.
  2.   El sistema muestra la tarifa por hora e intensidad horaria propuestas por el ofertador.
  3.   El cliente inicia una solicitud de compra, aceptando la propuesta original o contraproponiendo una
       tarifa/intensidad horaria distinta. La transacción se marca como “Negociando” (La intensidad horaria no es
       editable por el cliente).
  4.   El sistema notiﬁca al ofertador la propuesta recibida.
  5.   Por cada solicitud, el ofertador acepta la tarifa propuesta, o contrapropone una nueva tarifa y/o intensidad
       horaria.
  6.   Si el ofertador contrapropone, el cliente acepta o contrapropone nuevamente solo la tarifa.
  7.   El intercambio continúa sin límite de rondas hasta que una de las partes acepta la propuesta vigente.
  8.   Cuando el cliente acepta una propuesta, el sistema marca esa negociación como acordada y cancela
       automáticamente las demás solicitudes abiertas del cliente para ese mismo servicio buscado.
  9.    El sistema marca la transacción como activa entre cliente y ofertador, y notiﬁca a ambas partes.
 10.   El cliente conﬁrma la ﬁnalización del servicio una vez recibido.
 11.   El sistema marca la transacción como cerrada.
```

## Página PDF 6
```text
   04 Identiﬁcación de Objetos
    Sustantivos Caso 1:
                                                                           Verbos Caso 1:
                                                                           Selecciona: Descartado (acción de interfaz)
    Cliente: Clase Usuario (rol; mismo perﬁl que el ofertador)             Muestra: Descartado (acción de interfaz)
    Ofertador: Clase Usuario (rol; asociación con Servicio)                Publicado: Veriﬁcación y fecha de disponibilidad en servicio
    Servicio: Clase Servicio (entidad central con ciclo de vida propio)    Propuestas: Descartado (indica tarifa e intensidad originales
    Solicitud De Compra: Clase SolicitudCompra (agrupa cliente,            del Servicio)
    servicio, estado y propuestas)                                         Inicia: Método crear() en SolicitudCompra
    Negociación: Descartado (sinónimo de solicitud de compra)              Aceptando: Método aceptarPropuesta() en SolicitudCompra
    Propuesta: Clase Propuesta (tiene tarifa, intensidad, autor y fecha)   Contraproponiendo: Método contraproponer() en
    Tarifa Por Hora: Atributo (de Servicio y de Propuesta)                 SolicitudCompra
    intensidad horaria: Atributo (de Servicio y de Propuesta)              Notiﬁca: Asociado con clase y clase de diseño Notiﬁcador
    Sistema: Descartado (es el software, no el dominio)                    Recibida: Descartado (describe el ﬂujo)
    Intercambio: Descartado (es la lista de propuestas)                    Continúa: Descartado (describe el ﬂujo)
    Rondas: Atributo numeroRonda en Propuesta (solo ordena                 Marca: Método cambiarEstado() en SolicitudCompra
    propuestas)                                                            Cancela: Método cancelarSolicitudesAbiertas() (regla del paso
    Partes: Descartado (ya son cliente y ofertador)                        8)
                                                                           Conﬁrma: Método conﬁrmarFinalizacion() en SolicitudCompra



Nota: Nombres de clases y métodos son borradores
```

## Página PDF 7
```text
04 Identiﬁcación de Objetos
                                                                                 Palabra: Sustantivo en el contexto
                                                                                                    Palabra: Verbo
 Caso 2: Ofertar y Editar un Servicio                                    Solo se marca cada palabra en su primera
                                                                                                          aparición

 1.   El ofertador selecciona "Publicar servicio" o “Editar”.
 2.   El sistema muestra el formulario de publicación (categoría, descripción, tarifa por hora, intensidad
      horaria).
 3.   El ofertador completa los campos obligatorios y, opcionalmente, los complementarios (experiencia,
      especialidad, horarios de disponibilidad).
 4.   El ofertador conﬁrma la publicación.
 5.   El sistema valida los datos ingresados.
 6.   El sistema crea el servicio con estado disponible y lo asocia al perﬁl del ofertador.
 7.   El sistema conﬁrma la publicación y hace visible el servicio en el buscador y las categorías
      correspondientes.
```

## Página PDF 8
```text
   04 Identiﬁcación de Objetos
    Sustantivos Caso 2:
                                                                         Verbos Caso 2:
    Ofertador: Ya modelado (rol de Usuario)                              Selecciona: Descartado (acción de interfaz)
    Servicio: Ya modelado (Clase Servicio)                               Publicar: Método publicar() en Servicio
    Sistema: Descartado (es el software, no el dominio)                  Editar: Método editar() en Servicio
    Formulario: Descartado (elemento de interfaz)                        Muestra: Descartado (acción de interfaz)
    Publicación: Descartado (es el Servicio ya creado)                   Completa: Descartado (acción de interfaz)
    Categoría: Clase Categoria (sirve para navegar y ﬁltrar servicios)   Conﬁrma: Descartado (dispara publicar())
    Descripción: Atributo de Servicio                                    Valida: Método validar() en Servicio
    Tarifa Por Hora: Ya modelado (atributo de Servicio)                  Crea: Constructor de Servicio
    Intensidad Horaria: Ya modelado (atributo de Servicio)               Asocia: Asociación Perﬁl–Servicio (no es método)
    Campos Obligatorios: Descartado (agrupa atributos ya listados)       Hace Visible: Se resuelve con estado = DISPONIBLE
    Experiencia: Atributo de Servicio (opcional)
    Especialidad: Atributo de Servicio (opcional)
    Horarios De Disponibilidad: Atributo de Servicio (opcional)
    Datos Ingresados: Descartado (son los atributos del Servicio)
    Estado: Atributo estado en Servicio (enumeración EstadoServicio)
    Perﬁl: Clase Perﬁl (datos públicos del usuario; 1 a 1 con Usuario)
    Buscador: Descartado (funcionalidad, no entidad)


Nota: Nombres de clases y métodos son borradores
```

## Página PDF 9
```text
05 Modelo de Dominio
```

## Página PDF 10
```text
CU-01: Comprar / Negociar Servicio
                                     06




                                     Comportamiento
```

## Página PDF 11
```text
CU-02: Ofertar y Editar Servicio
                                   06




                                   Comportamiento
```

## Página PDF 12
```text
07   Diagrama de clases de diseño
```

## Página PDF 13
```text
08   Persistencia
```
