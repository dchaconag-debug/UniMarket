# Entrega 2: extracción de texto
Texto automático con distribución de página conservada. La especificación vigente está en [requisitos](../requisitos.md) y [diseño](../diseno.md). Los diagramas y capturas deben consultarse en el PDF y en las notas de revisión visual.

## Página PDF 1
```text
Entrega 2                                                                       Ingeniería de Software I


                         Entrega 2: Especificación de Requisitos
                            Sofía Franco Segura – sofrancos@unal.edu.co
                         Mariana García Dussan – margarciadu@unal.edu.co
                       Julián Gutiérrez Castillo – julgutierrezca@unal.edu.co
                     Juan Sebastián Camacho Plazas – jcamachopl@unal.edu.co
                          Camilo Esteban Páez Neuta – cpaezn@unal.edu.co
                           David Chacón Agudelo – dchacona@unal.edu.co

                                        Ingeniería de Software I
                       Facultad de Ingeniería, Universidad Nacional de Colombia
                                           Bogotá, Colombia

                                          Septiembre de 2026



1.   Actor-Goal list
Usuario agrupa las metas que tiene cualquier persona registrada, sea cliente u ofertador; en el diagrama,
Cliente y Ofertador son especializaciones suyas.




                                                   1
```

## Página PDF 2
```text
Entrega 2                                                                                     Ingeniería de Software I


Actor                   Meta                                                                      ¿Se desarrolla como
                                                                                                  caso de uso? (Sí / No)

                        Registrarse y verificar su cuenta con correo institucional o número de         Sí (CU-03)
                        teléfono
Usuario                 Crear y mantener actualizado su perfil (carrera, experiencia, disponi-         Sí (CU-04)
(cliente u ofertador)   bilidad, contacto)
                        Calificar y reseñar a la contraparte de un servicio ya cerrado                 Sí (CU-06)
                        Denunciar a la contraparte que incumplió lo acordado en un servicio            Sí (CU-07)
                        Reprogramar o cancelar un servicio ya acordado                                     No
                        Consultar su agenda de servicios activos con los horarios acordados                No

                        Encontrar servicios que respondan a su necesidad (por categoría, pa-           Sí (CU-05)
                        labra clave o nombre)
Cliente
                        Contratar un servicio, negociando la tarifa con el ofertador hasta lle-        Sí (CU-01)
                        gar a un acuerdo
                        Comprobar la confiabilidad de un ofertador (verificación, calificacio-             No
                        nes y reseñas) antes de contratarlo
                        Guardar servicios de interés para consultarlos más adelante                        No

                        Publicar un servicio en el catálogo o editar uno ya publicado                  Sí (CU-02)
                        Responder solicitudes de compra: aceptar, rechazar o contraproponer                No
Ofertador
                        tarifa e intensidad horaria
                        Retirar o pausar un servicio que ya no puede ofrecer                               No
                        Consultar su reputación (calificación promedio y reseñas recibidas)                No

                                         Cuadro 1: Actor-Goal List de ServiU.


2.      Casos de uso fully dressed
2.1.    Comprar un servicio




                                                             2
```

## Página PDF 3
```text
Ingeniería de Software I
COMPRAR UN SERVICIO
IDENTIFICADOR DEL CASO DE USO
COMPRA

 SISTEMA                                 NIVEL                                   ACTOR PRINCIPAL
  UniMarket                              Objetivo de Usuario                      Cliente

INTERESADOS E INTERESES
 Interesado                              Qué debe quedar protegido

  Cliente                                 Obtener el servicio legítima y clara, acorde a lo publicado

  Ofertador                               Recibir compensación según lo acordado, publicación y visibilidad del
                                         servicio

  Plataforma                             Controlar publicación de servicios y limitar la no-legitimidad en lo
                                         ofertado

PRECONDICIÓN
Existencia de un perfil de cliente y ofertador, que se haya publicado el servicio que se va a comprar y
que exista interés por realizar la compra y ofrecer el servicio

 GARANTÍA MÍNIMA                                              GARANTÍA DE ÉXITO
 (se cumple sin importar el resultado)                        (cuando la meta se cumple)


  Ninguna solicitud de compra queda marcada                   El cliente obtiene el servicio bajo las
 como cerrada sin el consentimiento explícito                 condiciones de tarifa e intensidad horaria
 de ambas partes sobre la tarifa y la intensidad              acordadas con el ofertador, y el servicio queda
 horaria finalmente acordadas.                                marcado como cerrado en el sistema.

  Toda negociación (propuestas,
 contrapropuestas y su resultado) queda
 registrada en el sistema.

DISPARADOR
(el evento que inicia el caso de uso)
El cliente selecciona la opción "Comprar" sobre un servicio publicado que le interesa.

ESCENARIO PRINCIPAL DE ÉXITO
(el “camino feliz” del caso de uso)

       1.​ El cliente selecciona un servicio publicado por un ofertador.
       2.​ El sistema muestra la tarifa por hora e intensidad horaria propuestas por el ofertador.
       3.​ El cliente inicia una solicitud de compra, aceptando la propuesta original o
           contraproponiendo una tarifa/intensidad horaria distinta. (La intensidad horaria no es editable
           por el cliente).
       4.​ El sistema notifica al ofertador la propuesta recibida.
       5.​ Por cada solicitud, el ofertador acepta la tarifa propuesta, o contrapropone una nueva tarifa
           y/o intensidad horaria.
```

## Página PDF 4
```text
      6.    Si el ofertador contrapropone, el cliente acepta o contrapropone nuevamente solo la tarifa.
    7. El intercambio continúa sin límite de rondas hasta que una de las partes acepta la propuesta
 vigente.
   8. Cuando el cliente acepta una propuesta, el sistema marca esa negociación como acordada y
 cancela automáticamente las demás solicitudes abiertas del cliente para ese mismo servicio
 buscado.
 9. El sistema marca el servicio como activo entre cliente y ofertador, y notifica a ambas partes.
 10. El cliente confirma la finalización del servicio una vez recibido.
 11. El sistema marca el servicio como cerrado.

EXTENSIONES
(Las excepciones que pueden surgir)

 Paso             Condición                                  Cómo se resuelve o se aborta

 3a               El cliente acepta la tarifa e intensidad   El sistema salta directo al paso 5 con la
                  original sin negociar                      propuesta original ya aceptada de facto

 5a               El ofertador contrapropone una nueva       Se regresa al paso 3 con el cliente evaluando
                  tarifa/intensidad en lugar de aceptar      la contrapropuesta

 5b               El ofertador rechaza la solicitud sin      El sistema cancela la solicitud y notifica al
                  contrapropuesta                            cliente; el servicio permanece disponible

 5c               Las partes no llegan a un acuerdo tras     El sistema cierra la negociación sin compra y
                  varios intercambios                        notifica a ambos

 6a               El cliente se retira de una negociación    El sistema cierra esa solicitud y notifica al
                  en curso                                   ofertador

 7a               El cliente no confirma la finalización     El sistema marca el servicio como cerrado
                  dentro de un plazo definido (una           automáticamente tras el plazo
                  semana)

 7b               Pasa una semana sin respuesta del          El sistema cierra automáticamente la solicitud
                  ofertador desde la última propuesta del    por inactividad y notifica al cliente
                  cliente

 8a               El cliente acepta propuestas de dos        El sistema aplica la primera aceptación
                  ofertadores casi al mismo tiempo           registrada, cancela las demás
                  (condición de carrera)                     automáticamente y notifica a los ofertadores
                                                             no seleccionados

 9a               El ofertador no confirma disponibilidad    El sistema cancela el servicio activo y notifica
                  después del acuerdo                        al cliente

 10a              El cliente no confirma la finalización     El sistema marca el servicio como cerrado
                  dentro de un plazo definido                automáticamente tras el plazo
```

## Página PDF 5
```text
Entrega 2                                 Ingeniería de Software I


2.2.   Ofertar y editar un servicio




                                      5
```

## Página PDF 6
```text
Ingeniería de Software I
OFERTAR Y EDITAR UN SERVICIO
IDENTIFICADOR DEL CASO DE USO
OFERTA

 SISTEMA                                 NIVEL                                 ACTOR PRINCIPAL
  UniMarket                              Objetivo de Usuario                    Ofertador

INTERESADOS E INTERESES
 Interesado                              Qué debe quedar protegido

 Ofertador                               Publicar su servicio de forma visible y atractiva para posibles
                                         clientes

 Cliente                                 Encontrar información completa y confiable sobre los servicios
                                         disponibles

 Plataforma                              Mantener el catálogo de servicios consistente, categorizado y con
                                         información mínima verificable

PRECONDICIÓN
Existencia de un perfil de ofertador verificado.

 GARANTÍA MÍNIMA                                            GARANTÍA DE ÉXITO
 (se cumple sin importar el resultado)                      (cuando la meta se cumple)


  Ninguna publicación queda visible en el                   El servicio queda publicado, visible en el
 buscador sin los campos obligatorios                       buscador y en los filtros por categoría, y
 completos (categoría, descripción, tarifa por              asociado al perfil del ofertador.
 hora e intensidad horaria).



DISPARADOR
(el evento que inicia el caso de uso)
El ofertador selecciona la opción "Publicar servicio" o “Editar servicio” desde su perfil.

ESCENARIO PRINCIPAL DE ÉXITO
(el “camino feliz” del caso de uso)

       1.​ El ofertador selecciona "Publicar servicio" o “Editar”.
       2.​ El sistema muestra el formulario de publicación (categoría, descripción, tarifa por hora,
           intensidad horaria).
       3.​ El ofertador completa los campos obligatorios y, opcionalmente, los complementarios
           (experiencia, especialidad, horarios de disponibilidad).
       4.​ El ofertador confirma la publicación.
       5.​ El sistema valida los datos ingresados.
       6.​ El sistema crea el servicio con estado disponible y lo asocia al perfil del ofertador. ​
```

## Página PDF 7
```text
      7. El sistema confirma la publicación y hace visible el servicio en el buscador y las categorías
         correspondientes.


EXTENSIONES
(Las excepciones que pueden surgir)

 Paso             Condición                                   Cómo se resuelve o se aborta

 1a               El ofertador selecciona "Editar             El sistema carga el formulario prellenado con
                  servicio" sobre uno ya publicado, en        los datos actuales del servicio y continúa en el
                  lugar de crear uno nuevo                    paso 3

 3a               El ofertador no completa un campo           El sistema le impide avanzar al paso 4 y
                  obligatorio                                 resalta el campo faltante

 4a               El servicio editado tiene una               El sistema bloquea la edición de tarifa e
                  negociación o transacción en curso          intensidad horaria hasta que la negociación
                  (ver CU-01)                                 en curso se resuelva; el resto de campos sí
                                                              puede editarse

 5a               Los datos ingresados no cumplen las         El sistema muestra un mensaje de error y
                  validaciones (ej. tarifa negativa o cero)   regresa al paso 3

 5b               El perfil del ofertador aún no está         El sistema bloquea la publicación y redirige al
                  verificado                                  ofertador al flujo de verificación de perfil

 6a               El sistema detecta un servicio muy          El sistema advierte al ofertador y le pregunta
                  similar ya publicado por el mismo           si desea continuar o editar la publicación
                  ofertador                                   existente

 6b               El sistema detecta un servicio muy          El sistema advierte al ofertador y le pregunta
                  similar ya publicado por el mismo           si desea continuar o editar la publicación
                  ofertador (solo aplica a publicación        existente
                  nueva)
```

## Página PDF 8
```text
Entrega 2                     Ingeniería de Software I


2.3.   Verificar perfil




                          8
```

## Página PDF 9
```text
Ingeniería de Software I
VERIFICAR PERFIL
IDENTIFICADOR DEL CASO DE USO
VERIFICACION

 SISTEMA                                 NIVEL                                 ACTOR PRINCIPAL
  UniMarket                              Objetivo de Usuario                    Usuario

INTERESADOS E INTERESES
 Interesado                              Qué debe quedar protegido

 Usuario                                 Poder participar plenamente en la plataforma (publicar y comprar
                                         servicios) con un proceso de verificación rápido

 Plataforma                              Asegurar que cada perfil corresponde a una persona real de la
                                         comunidad universitaria, reduciendo perfiles falsos

 Otros usuarios                          Confiar en que quienes publican o compran servicios son cuentas
                                         verificadas

PRECONDICIÓN
El usuario ha creado una cuenta ingresando un correo institucional o un número de teléfono, y esta
aún no ha sido verificada.

 GARANTÍA MÍNIMA                                            GARANTÍA DE ÉXITO
 (se cumple sin importar el resultado)                      (cuando la meta se cumple)


  Ningún perfil queda marcado como verificado                El perfil del usuario queda marcado como
 sin haber completado el mecanismo de                       verificado y habilitado para publicar y comprar
 verificación; todo intento de verificación                 servicios.
 (exitoso o fallido) queda registrado.



DISPARADOR
(el evento que inicia el caso de uso)
El usuario se registra en la plataforma o solicita verificar su cuenta desde su perfil.

ESCENARIO PRINCIPAL DE ÉXITO
(el “camino feliz” del caso de uso)

       1.​ El usuario ingresa su correo institucional o número de teléfono.
       2.​ El sistema envía un código de verificación al medio ingresado.
       3.​ El usuario ingresa el código recibido.
       4.​ El sistema valida el código contra el enviado.
       5.​ El sistema marca el perfil como verificado.
       6.​ El sistema habilita las funciones de publicar y comprar servicios para ese perfil.

EXTENSIONES
(Las excepciones que pueden surgir)
```

## Página PDF 10
```text
Paso   Condición                                 Cómo se resuelve o se aborta

1a     El correo ingresado no corresponde al     El sistema rechaza el correo y solicita uno
       dominio institucional                     válido o el número de teléfono como
                                                 alternativa

3a     El usuario ingresa un código incorrecto   El sistema muestra un error y permite
                                                 reintentar hasta un número máximo de
                                                 intentos

3b     El código expira antes de ser             El sistema permite solicitar el reenvío de un
       ingresado (5 minutos)                     nuevo código

3c     El usuario agota el número máximo de      El sistema bloquea temporalmente nuevos
       intentos permitidos (3)                   intentos de verificación para ese medio

4a     El código es válido pero ya expiró        El sistema rechaza el código y regresa al
                                                 paso 2
```

## Página PDF 11
```text
Entrega 2                                  Ingeniería de Software I


2.4.   Crear y actualizar un perfil




                                      11
```

## Página PDF 12
```text
Ingeniería de Software I
CREAR Y ACTUALIZAR UN PERFIL
IDENTIFICADOR DEL CASO DE USO
PERFIL

 SISTEMA                                 NIVEL                                 ACTOR PRINCIPAL
  UniMarket                              Objetivo de Usuario                    Usuario

INTERESADOS E INTERESES
 Interesado                              Qué debe quedar protegido

 Usuario                                 Mostrar información relevante y actualizada de sí mismo para
                                         atraer clientes u ofrecer servicios con confianza

 Otros usuarios                          Contar con información confiable y vigente del perfil para decidir
                                         si contratar o comprar

 Plataforma                              Mantener perfiles con información clara, estructurada y coherente
                                         en todo momento

PRECONDICIÓN
El usuario tiene una cuenta creada en la plataforma.

 GARANTÍA MÍNIMA                                            GARANTÍA DE ÉXITO
 (se cumple sin importar el resultado)                      (cuando la meta se cumple)


  Ningún perfil queda con campos obligatorios                El perfil queda creado o actualizado con la
 vacíos tras un intento de creación o                       información ingresada y visible para otros
 actualización; todo cambio queda registrado                usuarios donde corresponda (búsquedas,
 con su fecha.                                              servicios publicados, negociaciones en curso).



DISPARADOR
(el evento que inicia el caso de uso)
 El usuario selecciona "Editar perfil" desde su cuenta, o completa su perfil por primera vez tras
registrarse.

ESCENARIO PRINCIPAL DE ÉXITO
(el “camino feliz” del caso de uso)

       1.​ El usuario selecciona "Editar perfil". O crea perfil tras ingresar por primera vez.
       2.​ El sistema muestra el formulario de perfil (carrera(s) y semestre, avance, intereses,
           experiencia, especialidad, teléfono de contacto, horarios de disponibilidad).
       3.​ El usuario completa o modifica los campos deseados.
       4.​ El usuario confirma los cambios.
       5.​ El sistema valida los datos ingresados.
       6.​ El sistema guarda el perfil actualizado.
```

## Página PDF 13
```text
      7. El sistema confirma la actualización y refleja los cambios en todos los lugares donde el perfil
         es visible.

EXTENSIONES
(Las excepciones que pueden surgir)

 Paso             Condición                                Cómo se resuelve o se aborta

 1a               Es la primera vez que el usuario         El sistema crea un nuevo registro de perfil en
                  completa su perfil (no existe registro   lugar de actualizar uno existente, y continúa
                  previo)                                  en el paso 2

 3a               El usuario deja vacío un campo           El sistema le impide avanzar al paso 4 y
                  obligatorio                              resalta el campo faltante

 5a               Los datos ingresados no cumplen las      El sistema muestra un mensaje de error y
                  validaciones (ej. formato de teléfono    regresa al paso 3
                  inválido)

 5b               El usuario intenta eliminar un campo     El sistema bloquea el guardado hasta que el
                  obligatorio ya existente sin             campo quede completo
                  reemplazarlo

 6a               El usuario modifica su horario de        El sistema guarda el cambio para futuras
                  disponibilidad mientras tiene un         negociaciones, pero no afecta los horarios ya
                  servicio activo cuyo horario ya fue      acordados en servicios activos. Se envía una
                  acordado                                 alerta antes de actualizar el horario
```

## Página PDF 14
```text
Entrega 2                                   Ingeniería de Software I


2.5.   Navegar servicios disponibles




                                       14
```

## Página PDF 15
```text
Ingeniería de Software I
NAVEGAR SERVICIOS DISPONIBLES
IDENTIFICADOR DEL CASO DE USO
BUSQUEDA

 SISTEMA                                 NIVEL                                ACTOR PRINCIPAL
  UniMarket                              Objetivo de Usuario                   Cliente

INTERESADOS E INTERESES
 Interesado                              Qué debe quedar protegido

 Cliente                                 Encontrar rápidamente servicios relevantes según su necesidad

 Ofertador                               Que su servicio sea visible ante clientes potenciales que buscan
                                         en su categoría

 Plataforma                              Mantener resultados de búsqueda relevantes y consistentes con
                                         el catálogo publicado

PRECONDICIÓN
Existen uno o más servicios publicados en la plataforma.

 GARANTÍA MÍNIMA                                           GARANTÍA DE ÉXITO
 (se cumple sin importar el resultado)                     (cuando la meta se cumple)


  Solo se muestran servicios con estado                     El cliente encuentra uno o más servicios que
 disponible; ningún servicio cancelado, cerrado            cumplen sus criterios de búsqueda y puede
 o de un perfil bloqueado aparece en los                   ver el detalle de cualquiera de ellos.
 resultados.



DISPARADOR
(el evento que inicia el caso de uso)
El cliente accede a la página de servicios ofertados.

ESCENARIO PRINCIPAL DE ÉXITO
(el “camino feliz” del caso de uso)

       1.​ El cliente accede a la página de servicios ofertados.
       2.​ El sistema muestra el listado de servicios disponibles según la prioridad escogida (por
           defecto en orden ascendente según fecha de publicación)
       3.​ El cliente aplica uno o más filtros por categoría, o ingresa una palabra clave o nombre (de
           persona o de negocio) en el buscador.
       4.​ El sistema muestra los servicios que cumplen los criterios ingresados.
       5.​ El cliente selecciona un servicio del listado de resultados.
       6.​ El sistema muestra el detalle completo del servicio (descripción, categoría, tarifa por hora,
           intensidad horaria, perfil e información del ofertador, calificaciones asociadas).

EXTENSIONES
```

## Página PDF 16
```text
(Las excepciones que pueden surgir)

 Paso             Condición                                  Cómo se resuelve o se aborta

 3a               El cliente no aplica ningún filtro ni      El sistema mantiene el listado completo
                  búsqueda                                   mostrado en el paso 2

 4a               Ningún servicio cumple los criterios       El sistema muestra un mensaje de "sin
                  ingresados                                 resultados" y sugiere ajustar los filtros o la
                                                             búsqueda

 6a               El cliente guarda el servicio en una       El sistema añade el servicio a la colección de
                  colección para consulta posterior          guardados del cliente y confirma la acción

 6b               El servicio consultado deja de estar       El sistema notifica al cliente que el servicio ya
                  disponible mientras el cliente lo estaba   no está disponible y lo regresa al listado
                  viendo (ej. otro cliente lo cerró)
```

## Página PDF 17
```text
Entrega 2                  Ingeniería de Software I


2.6.   Dejar reseña




                      17
```

## Página PDF 18
```text
Ingeniería de Software I
DEJAR RESEÑA
IDENTIFICADOR DEL CASO DE USO
RESENA

 SISTEMA                                 NIVEL                                ACTOR PRINCIPAL
  UniMarket                              Objetivo de Usuario                   Usuario (ofertador o cliente)

INTERESADOS E INTERESES
 Interesado                              Qué debe quedar protegido

 Cliente/Ofertador (quien reseña)        Poder expresar su experiencia real con la contraparte tras una
                                         transacción

 Ofertador/Cliente (reseñado)            Que solo se le califique con base en transacciones efectivamente
                                         realizadas con él

 Plataforma                              Mantener la confiabilidad del sistema de calificaciones como
                                         mecanismo de confianza central del marketplace

PRECONDICIÓN
Existe un servicio previamente marcado como cerrado entre el usuario que reseña y la contraparte, y
dicho servicio aún no ha sido reseñado por el usuario actual.

 GARANTÍA MÍNIMA                                           GARANTÍA DE ÉXITO
 (se cumple sin importar el resultado)                     (cuando la meta se cumple)


  Ninguna reseña queda publicada si no está                 La reseña queda publicada, visible en el perfil
 asociada a un servicio cerrado entre ambas                de la contraparte reseñada, y asociada al
 partes; un usuario no puede dejar más de una              servicio cerrado que le dio origen.
 reseña por cada servicio cerrado.



DISPARADOR
(el evento que inicia el caso de uso)
El sistema notifica al usuario que puede dejar una reseña tras el cierre de un servicio, o el usuario
accede voluntariamente a la opción "Dejar reseña" desde el historial de servicios cerrados.

ESCENARIO PRINCIPAL DE ÉXITO
(el “camino feliz” del caso de uso)

       1.​ El usuario selecciona "Dejar reseña" sobre un servicio cerrado en su historial.
       2.​ El sistema muestra el formulario de reseña (calificación numérica y comentario).
       3.​ El usuario ingresa la calificación y, opcionalmente, un comentario.
       4.​ El usuario confirma el envío.
       5.​ El sistema valida que el servicio esté cerrado y que no exista ya una reseña del mismo
           usuario sobre ese servicio.
       6.​ El sistema publica la reseña y la asocia al perfil de la contraparte y al servicio cerrado.
```

## Página PDF 19
```text
      7.    El sistema actualiza la calificación promedio del perfil reseñado.

EXTENSIONES
(Las excepciones que pueden surgir)

 Paso             Condición                                 Cómo se resuelve o se aborta

 3a               El usuario no ingresa una calificación    El sistema le impide avanzar al paso 4 hasta
                  numérica (solo comentario)                que ingrese la calificación, que es obligatoria

 5a               El servicio aún no está cerrado           El sistema bloquea el acceso a "Dejar reseña"
                                                            para ese servicio

 5b               El usuario ya dejó una reseña sobre       El sistema bloquea un segundo envío y ofrece
                  ese mismo servicio                        la opción de editar la reseña existente

 6a               La contraparte reseñada tiene un          El sistema publica la reseña igualmente, pero
                  denuncio activo por rompimiento de        la marca como "en revisión" hasta que el
                  contrato sobre ese mismo servicio         denuncio se resuelva
```

## Página PDF 20
```text
Entrega 2                    Ingeniería de Software I


2.7.   Dejar denuncia




                        20
```

## Página PDF 21
```text
Ingeniería de Software I
DEJAR DENUNCIO
IDENTIFICADOR DEL CASO DE USO
DENUNCIA

 SISTEMA                                 NIVEL                                ACTOR PRINCIPAL
  UniMarket                              Objetivo de Usuario                   Denunciante (ofertador o
                                                                              cliente)

INTERESADOS E INTERESES
 Interesado                              Qué debe quedar protegido

 Denunciante                             Que su incumplimiento sufrido quede registrado formalmente y
                                         tenga una consecuencia real sobre la contraparte

 Denunciado                              No recibir sanciones por denuncios infundados o repetidos sobre
                                         el mismo servicio

 Plataforma                              Sancionar de forma consistente y automática a perfiles con
                                         incumplimientos recurrentes, sin depender de revisión manual

PRECONDICIÓN
Existe un servicio previamente marcado como cerrado o activo entre el denunciante y la contraparte, y
el denunciante no ha denunciado previamente ese mismo servicio.

 GARANTÍA MÍNIMA                                           GARANTÍA DE ÉXITO
 (se cumple sin importar el resultado)                     (cuando la meta se cumple)


 Ningún denuncio queda registrado sin estar                El denuncio queda registrado y asociado al
 asociado a un servicio real entre denunciante y           perfil del denunciado; si este acumula el
 denunciado; un mismo servicio no puede ser                umbral de denuncios válidos definido por la
 denunciado más de una vez por el mismo                    plataforma, se le aplica automáticamente la
 denunciante.                                              sanción correspondiente.



DISPARADOR
(el evento que inicia el caso de uso)
 El denunciante selecciona "Denunciar" sobre un servicio de su historial en el que considera que la
contraparte incumplió lo acordado.

ESCENARIO PRINCIPAL DE ÉXITO
(el “camino feliz” del caso de uso)

       1.​ El denunciante selecciona "Denunciar" sobre un servicio de su historial.
       2.​ El sistema muestra el formulario de denuncio (motivo y evidencia).
       3.​ El denunciante selecciona el motivo del incumplimiento y adjunta evidencia (texto y/o
           imagen).
       4.​ El denunciante confirma el envío.
       5.​ El sistema valida que el servicio exista entre ambas partes y que no haya sido denunciado
           antes por el mismo denunciante.
```

## Página PDF 22
```text
      6.​ El sistema registra el denuncio, asociado al servicio y al perfil denunciado.
      7.    El sistema evalúa si el perfil denunciado acumula, en denuncios válidos y de servicios
           distintos, el umbral definido para aplicar sanción.
      8.    El sistema confirma al denunciante que el denuncio quedó registrado.

EXTENSIONES
(Las excepciones que pueden surgir)

 Paso             Condición                                Cómo se resuelve o se aborta

 3a               El denunciante no adjunta evidencia      El sistema le impide avanzar al paso 4 hasta
                                                           que adjunte al menos un elemento de
                                                           evidencia

 5a               El servicio no existe entre el           El sistema rechaza el denuncio y notifica al
                  denunciante y el denunciado              denunciante

 5b               El denunciante ya denunció ese           El sistema bloquea un segundo denuncio
                  mismo servicio previamente               sobre el mismo servicio

 7a               El perfil denunciado alcanza el umbral   El sistema aplica automáticamente la sanción
                  de denuncios válidos                     predefinida (suspensión de publicaciones,
                                                           marca de "en observación", o bloqueo del
                                                           perfil) y notifica al denunciado

 7b               El perfil denunciado no alcanza el       El denuncio queda registrado en su historial,
                  umbral                                   sin sanción inmediata, y cuenta para futuros
                                                           umbrales
```

## Página PDF 23
```text
Entrega 2                Ingeniería de Software I


1.   Diagrama UML




                    21
```

## Página PDF 24
```text
                                                    UniMarket


                                            CU-05: Navegar Servicios
                                                  Disponibles




                                                CU-01: Comprar /
                                                Negociar Servicio

                                                              <<extend>>
                                                           [servicio cerrado]


            Cliente                            CU-06: Dejar Reseña



                                                 CU-02: Ofertar y
                                                  Editar Servicio
                                  <<extend>>
                                                              <<extend>>
                        [incumplimiento de lo acordado]
                                                          [perfil no verificado]
           Ofertador
                                              CU-03: Verificar Perfil
Usuario



                                             CU-07: Dejar Denuncio


          Denunciante

                                                  CU-04: Crear y
                                                  Actualizar Perfil
```

## Página PDF 25
```text
Entrega 2                                                                                  Ingeniería de Software I


2.       Historias de usuario
2.0.1.     COMPRA. Comprar un servicio

COMPRA-CLI-H01. Contratar con las condiciones publicadas                     Como cliente, quiero solicitar un ser-
vicio con la tarifa e intensidad horaria publicadas, para contratarlo sin tener que negociar otras condiciones.

Criterios de aceptación:

         [ ] Antes de enviar la solicitud, el sistema muestra la tarifa y la intensidad horaria del servicio selec-
         cionado.
         [ ] La solicitud registra la aceptación del cliente de esas condiciones y notifica al ofertador.
         [ ] Si el ofertador acepta la solicitud, el sistema registra el acuerdo, deja el servicio activo entre ambas
         partes y les notifica; no registra un acuerdo sin la aceptación de ambas partes.
         [ ] Si el ofertador rechaza la solicitud sin contrapropuesta, el sistema cancela la solicitud, notifica al
         cliente y mantiene disponible el servicio publicado.

Caso de uso relacionado: Comprar un servicio.

COMPRA-CLI-H02. Negociar las condiciones de contratación                    Como cliente, quiero intercambiar pro-
puestas con el ofertador antes de contratar, para acordar una tarifa que se ajuste a mi necesidad.

Criterios de aceptación:

         [ ] El cliente puede proponer y contraproponer una tarifa, pero no modificar la intensidad horaria; el
         ofertador puede contraproponer ambos valores.
         [ ] El sistema registra cada propuesta y contrapropuesta, y notifica al ofertador cuando recibe una
         propuesta del cliente.
         [ ] El intercambio permite nuevas rondas sin un límite fijo mientras la solicitud siga abierta; al aceptar
         la propuesta vigente, el sistema conserva la tarifa y la intensidad aceptadas por ambas partes.
         [ ] Al alcanzarse el acuerdo, el sistema registra su resultado, activa el servicio entre las partes y les
         notifica.

Caso de uso relacionado: Comprar un servicio.

COMPRA-CLI-H03. Terminar una negociación sin compra                      Como cliente, quiero que una solicitud se
cierre cuando me retiro o el ofertador no responde, para dejar de mantener una negociación que no voy a
concretar.

Criterios de aceptación:

         [ ] Si el cliente se retira de una negociación abierta, el sistema cierra esa solicitud y notifica al oferta-
         dor.
         [ ] Si transcurre una semana sin respuesta del ofertador desde la última propuesta del cliente, el sistema
         cierra automáticamente la solicitud y notifica al cliente.


                                                          23
```

## Página PDF 26
```text
Entrega 2                                                                              Ingeniería de Software I


         [ ] El resultado queda registrado como negociación sin compra y no genera un servicio activo ni un
         acuerdo entre las partes.

Caso de uso relacionado: Comprar un servicio.

COMPRA-CLI-H04. Evitar contrataciones simultáneas para la misma búsqueda                     Como cliente, quie-
ro que al aceptar una oferta se cancelen mis otras solicitudes para el mismo servicio buscado, para evitar
contratar por duplicado.

Criterios de aceptación:

         [ ] Cuando el cliente acepta una propuesta, el sistema marca esa negociación como acordada y cancela
         sus demás solicitudes abiertas para el mismo servicio buscado.
         [ ] Si el cliente acepta propuestas de dos ofertadores casi simultáneamente, solo la primera aceptación
         registrada genera el acuerdo.
         [ ] El sistema notifica a los ofertadores no seleccionados y conserva el resultado de las negociaciones
         canceladas.

Caso de uso relacionado: Comprar un servicio.

COMPRA-CLI-H05. Confirmar la finalización del servicio                 Como cliente, quiero confirmar que recibí
el servicio contratado, para dejar constancia de su finalización.

Criterios de aceptación:

         [ ] La confirmación se realiza sobre el servicio activo que tiene registradas la tarifa y la intensidad
         horaria acordadas por ambas partes.
         [ ] Cuando el cliente confirma la finalización, el sistema cambia ese servicio a estado cerrado.
         [ ] El cierre conserva el registro de las propuestas, contrapropuestas y el acuerdo que dio origen al
         servicio.

Caso de uso relacionado: Comprar un servicio.

2.0.2.    OFERTA. Ofertar y editar un servicio

OFERTA-OFE-H01. Publicar un servicio                  Como ofertador, quiero publicar un servicio con sus condi-
ciones y descripción, para darme a conocer ante estudiantes que necesitan lo que ofrezco.

Criterios de aceptación:

         [ ] Solo un ofertador con perfil verificado puede publicar; si no está verificado, el sistema bloquea la
         publicación y lo dirige a la verificación.
         [ ] El sistema exige categoría, descripción, tarifa por hora e intensidad horaria; resalta los campos
         faltantes e impide publicar hasta completarlos.




                                                           24
```

## Página PDF 27
```text
Entrega 2                                                                                Ingeniería de Software I


         [ ] Si los datos no cumplen las validaciones, incluida una tarifa menor o igual a cero, el sistema
         informa el error y permite corregirlos sin publicar el servicio.
         [ ] Con datos válidos y confirmación del ofertador, el servicio queda disponible, asociado a su perfil y
         visible en el buscador y en su categoría; el sistema confirma la publicación.

Caso de uso relacionado: Ofertar y editar un servicio.

OFERTA-OFE-H02. Actualizar un servicio publicado                  Como ofertador, quiero editar un servicio que
ya publiqué, para mantener mi oferta actualizada sin alterar las condiciones de una negociación en curso.

Criterios de aceptación:

         [ ] Al seleccionar la edición de un servicio propio, el sistema muestra el formulario con sus datos
         actuales.
         [ ] El sistema solo guarda la edición si los campos obligatorios están completos y los datos cumplen
         las validaciones de publicación.
         [ ] Si existe una negociación o transacción en curso, el sistema bloquea la edición de tarifa e intensidad
         horaria hasta que la negociación en curso se resuelva y permite editar los demás campos.
         [ ] Tras confirmar una edición válida, la publicación existente refleja los cambios y el sistema confirma
         la actualización.

Caso de uso relacionado: Ofertar y editar un servicio.

OFERTA-OFE-H03. Revisar una posible publicación duplicada                     Como ofertador, quiero recibir una
advertencia si intento publicar un servicio similar a uno mío, para decidir si necesito una nueva publicación
o actualizar la existente.

Criterios de aceptación:

         [ ] Antes de crear una nueva publicación, si el sistema detecta un servicio muy similar del mismo
         ofertador, muestra una advertencia.
         [ ] La advertencia permite elegir entre continuar con la publicación nueva o editar la existente.
         [ ] Si el ofertador continúa, se aplican las validaciones de publicación; si elige editar, el sistema carga
         los datos del servicio existente.

Caso de uso relacionado: Ofertar y editar un servicio.

2.0.3.    VERIFICACION. Verificar perfil

VERIFICACION-USR-H01. Verificar la cuenta mediante un código                      Como usuario, quiero verificar
mi cuenta con un código enviado a mi correo institucional o teléfono, para habilitar la publicación y la
compra de servicios.

Criterios de aceptación:



                                                         25
```

## Página PDF 28
```text
Entrega 2                                                                               Ingeniería de Software I


         [ ] El sistema envía el código al correo institucional o al teléfono ingresado; si el correo no pertenece
         al dominio institucional, lo rechaza y solicita uno válido o un teléfono como alternativa.
         [ ] El perfil solo queda verificado cuando el código ingresado coincide con el enviado y no ha expirado.
         [ ] Una verificación exitosa habilita las funciones de publicar y comprar servicios.
         [ ] El sistema registra los intentos de verificación, tanto exitosos como fallidos, y mantiene el perfil
         sin verificar cuando el intento falla.

Caso de uso relacionado: Verificar perfil.

VERIFICACION-USR-H02. Recuperar una verificación fallida                   Como usuario, quiero corregir un có-
digo incorrecto o solicitar otro si expiró, para completar la verificación de mi cuenta.

Criterios de aceptación:

         [ ] Ante un código incorrecto, el sistema informa el error y permite reintentar mientras no se hayan
         agotado los tres intentos permitidos.
         [ ] El código expira a los cinco minutos; el sistema rechaza un código vencido y permite solicitar el
         envío de uno nuevo.
         [ ] Al agotarse los tres intentos, el sistema bloquea temporalmente nuevos intentos para ese medio de
         verificación.
         [ ] Los intentos fallidos quedan registrados y no habilitan la compra ni la publicación de servicios.

Caso de uso relacionado: Verificar perfil.

2.0.4.     PERFIL. Crear y actualizar un perfil

PERFIL-USR-H01. Completar el perfil por primera vez               Como usuario, quiero completar mi perfil con
mi información académica y de servicios, para que otros usuarios conozcan mi experiencia antes de decidir
una contratación.

Criterios de aceptación:

         [ ] Para una cuenta que todavía no tiene perfil, el sistema muestra el formulario y crea un registro
         nuevo al guardar datos válidos.
         [ ] Si falta un campo obligatorio, el sistema lo resalta e impide confirmar; si un dato es inválido, como
         el formato del teléfono, informa el error y permite corregirlo.
         [ ] El perfil creado conserva los datos ingresados y queda visible para otros usuarios en los lugares
         donde corresponda.
         [ ] El sistema confirma la creación y registra la fecha del cambio.

Caso de uso relacionado: Crear y actualizar un perfil.

PERFIL-USR-H02. Mantener el perfil actualizado               Como usuario, quiero actualizar mi información y
disponibilidad, para mostrar datos vigentes y conservar los compromisos de mis servicios activos.


                                                        26
```

## Página PDF 29
```text
Entrega 2                                                                              Ingeniería de Software I


Criterios de aceptación:

         [ ] El sistema muestra los datos actuales y guarda los cambios confirmados solo si los datos son válidos
         y ningún campo obligatorio queda vacío.
         [ ] Si el usuario intenta eliminar un dato obligatorio sin reemplazarlo, el sistema bloquea el guardado
         hasta completarlo.
         [ ] Si modifica la disponibilidad teniendo un servicio activo con horario acordado, el sistema muestra
         una alerta antes de actualizar y aplica el cambio únicamente a futuras negociaciones, conservando los
         horarios ya acordados.
         [ ] El sistema registra la fecha del cambio, confirma la actualización y la refleja en los lugares donde
         el perfil es visible.

Caso de uso relacionado: Crear y actualizar un perfil.

2.0.5.     BUSQUEDA. Navegar servicios disponibles

BUSQUEDA-CLI-H01. Consultar el catálogo y los detalles de un servicio               Como cliente, quiero explo-
rar los servicios disponibles y consultar sus detalles, para comparar ofertas de la comunidad universitaria en
un solo lugar antes de contratar.

Criterios de aceptación:

         [ ] El listado muestra únicamente servicios disponibles y excluye los cancelados, los cerrados y los
         publicados por perfiles bloqueados.
         [ ] El listado se ordena según la prioridad escogida por el cliente; por defecto, en orden ascendente
         según la fecha de publicación. Sin filtros ni búsqueda, se mantiene el catálogo completo de servicios
         disponibles.
         [ ] Al seleccionar un servicio, el sistema muestra descripción, categoría, tarifa por hora, intensidad
         horaria, perfil e información del ofertador y calificaciones asociadas.
         [ ] Si el servicio deja de estar disponible mientras el cliente lo consulta, el sistema le informa y lo
         regresa al listado.

Caso de uso relacionado: Navegar servicios disponibles.

BUSQUEDA-CLI-H02. Buscar servicios según mi necesidad                 Como cliente, quiero buscar servicios por
categoría, palabra clave o nombre, para encontrar ofertas que correspondan a lo que necesito.

Criterios de aceptación:

         [ ] El cliente puede aplicar uno o más filtros por categoría y realizar una búsqueda por palabra clave o
         nombre de persona o negocio.
         [ ] Los resultados cumplen los criterios ingresados y contienen únicamente servicios disponibles de
         perfiles no bloqueados.



                                                        27
```

## Página PDF 30
```text
Entrega 2                                                                               Ingeniería de Software I


         [ ] Si no hay coincidencias, el sistema muestra un mensaje de "sin resultados sugiere ajustar los filtros
                                                                                       2

         o la búsqueda.
         [ ] El cliente puede abrir el detalle de un servicio encontrado y consultar sus condiciones e información
         del ofertador.

Caso de uso relacionado: Navegar servicios disponibles.

BUSQUEDA-CLI-H03. Guardar un servicio para consultarlo después                     Como cliente, quiero guardar
un servicio que me interesa en una colección, para consultarlo posteriormente sin repetir la búsqueda.

Criterios de aceptación:

         [ ] Desde el detalle de un servicio, el cliente puede seleccionar la acción de guardarlo.
         [ ] El sistema añade el servicio a la colección de guardados del cliente y confirma la acción.

Caso de uso relacionado: Navegar servicios disponibles.

2.0.6.    RESENA. Dejar reseña

RESENA-USR-H01. Calificar una experiencia de servicio                 Como usuario que participó como cliente u
ofertador, quiero calificar a mi contraparte después de finalizar un servicio, para que otros usuarios cuenten
con referencias de una experiencia real antes de contratar.

Criterios de aceptación:

         [ ] Solo se puede publicar la reseña de un servicio cerrado entre el usuario que reseña y la contraparte;
         si el servicio sigue activo, el sistema bloquea la opción.
         [ ] La calificación numérica es obligatoria y el comentario es opcional; el sistema impide enviar una
         reseña que tenga únicamente comentario.
         [ ] La reseña queda asociada al servicio cerrado y al perfil de la contraparte, donde es visible, y el
         sistema actualiza la calificación promedio de ese perfil.
         [ ] El sistema no publica más de una reseña del mismo usuario para el mismo servicio cerrado.

Caso de uso relacionado: Dejar reseña.

RESENA-USR-H02. Acceder a la edición de una reseña existente                   Como usuario que ya reseñó un
servicio, quiero acceder a la edición de mi reseña existente, para corregir mi valoración sin crear otra sobre
la misma experiencia.

Criterios de aceptación:

         [ ] Si el usuario intenta reseñar de nuevo un servicio que ya calificó, el sistema bloquea el segundo
         envío.
         [ ] En ese caso, el sistema ofrece la opción de editar la reseña existente del usuario, conservando una
         sola reseña por usuario y servicio.


                                                         28
```

## Página PDF 31
```text
Entrega 2                                                                             Ingeniería de Software I


Caso de uso relacionado: Dejar reseña.

RESENA-USR-H03. Publicar una reseña cuando existe una denuncia activa                  Como usuario que parti-
cipó en un servicio cerrado, quiero publicar mi reseña aunque exista una denuncia activa contra mi contra-
parte por ese servicio, para dejar constancia de mi experiencia mientras se resuelve la denuncia.

Criterios de aceptación:

         [ ] La existencia de una denuncia activa por rompimiento de contrato contra la contraparte, sobre el
         mismo servicio, no impide publicar una reseña que cumpla las validaciones del caso de uso.
         [ ] El sistema publica la reseña asociada al perfil y al servicio correspondiente, con la marca .en
         revisión"mientras la denuncia permanece sin resolver.

Caso de uso relacionado: Dejar reseña.

2.0.7.     DENUNCIA. Dejar denuncio

DENUNCIA-USR-H01. Registrar un incumplimiento con evidencia                    Como denunciante que participó
como cliente u ofertador, quiero denunciar un incumplimiento y adjuntar evidencia, para que quede regis-
trado formalmente sobre el servicio y la contraparte.

Criterios de aceptación:

         [ ] El denunciante puede seleccionar un servicio activo o cerrado de su historial e indicar el motivo
         del incumplimiento.
         [ ] El sistema exige al menos un elemento de evidencia en texto o imagen e impide confirmar el envío
         si falta.
         [ ] Si el servicio existe entre ambas partes y no ha sido denunciado antes por ese denunciante, el
         sistema registra la denuncia asociada al servicio y al perfil denunciado.
         [ ] El sistema confirma al denunciante que la denuncia quedó registrada.

Caso de uso relacionado: Dejar denuncio.

DENUNCIA-USR-H02. Evitar denuncias duplicadas o ajenas al servicio                   Como denunciante, quiero
que el sistema valide la relación con el servicio y detecte denuncias repetidas, para registrar mi reclamación
una sola vez sobre una transacción en la que participé.

Criterios de aceptación:

         [ ] Si el servicio no existe entre denunciante y denunciado, el sistema rechaza la denuncia y comunica
         el rechazo al denunciante.
         [ ] Si el mismo denunciante ya denunció ese servicio, el sistema bloquea un segundo registro.
         [ ] Una denuncia rechazada por cualquiera de estas condiciones no crea un nuevo registro asociado al
         perfil denunciado ni incrementa el conteo para sanciones.



                                                        29
```

## Página PDF 32
```text
Entrega 2                                                                           Ingeniería de Software I


Caso de uso relacionado: Dejar denuncio.

DENUNCIA-USR-H03. Aplicar las consecuencias de denuncias válidas acumuladas                    Como denun-
ciante, quiero que las denuncias válidas cuenten para aplicar la sanción definida cuando se alcance el umbral,
para que los incumplimientos recurrentes tengan una consecuencia en la plataforma.

Criterios de aceptación:

      [ ] El sistema evalúa el umbral usando denuncias válidas de servicios distintos asociadas al perfil
      denunciado.
      [ ] Si no se alcanza el umbral, la denuncia queda en el historial, no se aplica una sanción inmediata y
      cuenta para futuras evaluaciones.
      [ ] Si se alcanza el umbral, el sistema aplica automáticamente la sanción predefinida por la plataforma
      y notifica al denunciado.

Caso de uso relacionado: Dejar denuncio.


3.   GitHub del proyecto
https://github.com/users/nauj2024/projects/1/views/1


4.   Acta de la entrevista (Anexo)




                                                     30
```

## Página PDF 33
```text
Acta de entrevista
Ingeniería de Software I · SIA 2016701 · Grupo 4 · 2026-II

Hagan una copia de este documento para su equipo. Llenen una acta por cada entrevista realizada. Agreguen tantas filas como
necesiten.

1. Datos de la entrevista
 Equipo                             Acta n.º                           Fecha                              Duración real

  Atenea                            2                                  06 de octubre de 2026              9:55
El identificador del acta es el nombre del equipo y un número consecutivo. Por ejemplo: ARES-02.

 Persona entrevistada                                                  Cargo o relación con el problema

  Julian Andres Foglia Wilches                                          Tutor de Cálculo Diferencial, aritmética, álgebra,
                                                                       termodinámica y fluidos


Quiénes condujeron la entrevista

 Quién lidera                       Quién toma nota                    Quién controla el tiempo           Quién prepara el cierre

 Julian David Gutierrez              Mariana Garcia Dussan              Mariana Garcia Dussan              Mariana Garcia Dussan
 Castillo


Quiénes revisaron la grabación

 Nombre                                                                Nombre

  David Chacón Agudelo
Solo cuatro integrantes entran a la entrevista. Quien no entra escucha después la grabación y llena la sección 6 de esta acta: en frío se
detectan ambigüedades que en la sala pasaron desapercibidas. En la siguiente entrevista se rotan los papeles.

2. Cómo trabaja hoy esa persona
Describan el proceso actual tal como lo contó la persona, en su lenguaje y no en lenguaje de sistema. Incluyan al menos una cita
textual entre comillas.

 Lo que dijo la persona                                                Lo que interpretamos nosotros

 Dependiendo de la dificultad de cada materia se genera                Entendemos que según qué tan avanzada es la materia el
 el valor de la clase                                                  asigna un valor por hora a la tutoría

 Pérdida de tiempo por transportes, sobre todo cuando                   A veces él se dirige al lugar en donde tiene la tutoría y le
 me cancelan muy encima                                                cancelan, pierde su tiempo de traslado y dinero

  Depende que logre convencer a la persona de que la                    Muchas veces las personas son indecisas sobre si acceder
 tutoría va a ser significativa para su proceso                        a la tutoría, y el debe ser un poco persuasivo para que lo
                                                                       contraten, esto lo podríamos arreglar con unas
                                                                       calificaciones y un ranking para ayudar a los tutores

 Fijo mi tarifa al inicio de la conversación, la negociación           siempre pone un valor desde el inicio, pero puede hacer
 depende de lo que me dice cada estudiante                             descuentos según las necesidades de los estudiantes


3. Qué le molesta o le cuesta
Problemas que la persona nombró, con al menos una cita textual entre comillas. No incluyan aquí problemas que dedujo el
equipo: esos van en la columna de la derecha.
```

## Página PDF 34
```text
 Lo que dijo la persona                                                 Lo que interpretamos nosotros

 Las cancelaciones sobre el horario, muy cerca de la hora                Para el es muy molesto que le cancelen sin motivo ni
 acordada                                                               razón y nada de reclamación al cliente, ya que el si se ve
                                                                        afectado


4. Términos del oficio
Palabras que usó la persona y que tuvieron que preguntar qué significaban.

 Término                                                                Qué significa




5. Qué quedó ambiguo
La llena quien revisó la grabación. Respuestas que admiten más de una lectura y que nadie alcanzó a aclarar; déjenlas sin
resolver.
¿Cómo decide que tan difícil es una materia?
¿Alguna vez si ha penalizado un cliente o ha hecho un reclamo a alguno de ellos?




6. Preguntas para la próxima entrevista
Derivadas de la sección anterior.


¿Qué factores específicos evalúas para determinar si una materia es más "difícil" que otra al definir su costo por
hora? (Por ejemplo: nivel académico, preparación requerida, disponibilidad de material, demanda).


¿Tienes un rango o tabla de precios fija por área (p. ej. cálculo vs. termodinámica) o varía de forma completamente
personalizada según cada estudiante?


¿En qué casos o bajo qué condiciones específicas estás dispuesto a ofrecer un descuento o negociar tu tarifa
inicial?


¿Qué porcentaje de tus tutorías son presenciales vs. virtuales?



El equipo conserva la grabación de la entrevista y la mantiene disponible si el docente la solicita.
```

## Página PDF 35
```text
Entrega 2                                                      Ingeniería de Software I




            Figura 1: Tablero del backlog en GitHub Projects




                                  33
```

## Página PDF 36
```text
Entrega 2                                                      Ingeniería de Software I




            Figura 2: Tablero del backlog en GitHub Projects




                                  34
```

## Página PDF 37
```text
Entrega 2                                                      Ingeniería de Software I




            Figura 3: Tablero del backlog en GitHub Projects




                                  35
```
