package com.atenea.unimarket.negocio;

import java.text.NumberFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atenea.unimarket.datos.modelo.EstadoServicio;
import com.atenea.unimarket.datos.modelo.EstadoSolicitud;
import com.atenea.unimarket.datos.modelo.Propuesta;
import com.atenea.unimarket.datos.modelo.Servicio;
import com.atenea.unimarket.datos.modelo.SolicitudCompra;
import com.atenea.unimarket.datos.modelo.TipoNotificacion;
import com.atenea.unimarket.datos.modelo.Usuario;
import com.atenea.unimarket.datos.repositorio.PropuestaRepository;
import com.atenea.unimarket.datos.repositorio.ServicioRepository;
import com.atenea.unimarket.datos.repositorio.SolicitudCompraRepository;
import com.atenea.unimarket.datos.repositorio.UsuarioRepository;

@Service
public class SolicitudCompraService {

    private static final Locale COLOMBIA = Locale.forLanguageTag("es-CO");

    private final SolicitudCompraRepository solicitudes;
    private final PropuestaRepository propuestas;
    private final ServicioRepository servicios;
    private final UsuarioRepository usuarios;
    private final Notificador notificador;
    private final Clock reloj;

    public SolicitudCompraService(SolicitudCompraRepository solicitudes, PropuestaRepository propuestas,
            ServicioRepository servicios, UsuarioRepository usuarios, Notificador notificador, Clock reloj) {
        this.solicitudes = solicitudes;
        this.propuestas = propuestas;
        this.servicios = servicios;
        this.usuarios = usuarios;
        this.notificador = notificador;
        this.reloj = reloj;
    }

    /**
     * COMPRA-CLI-H01 / HU-04: the client requests an available service. The request starts
     * NEGOCIANDO with a first-round proposal by the client, and the offerer is notified. The
     * weekly hours are always the published ones: only the offerer can change them. Without a
     * rate the client takes the published conditions as they are.
     */
    @Transactional
    public SolicitudCompra crear(Integer clienteId, Integer servicioId, Integer tarifaPorHora) {
        Usuario cliente = usuarios.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe."));
        Servicio servicio = servicios.findById(servicioId).orElseThrow(ServicioNoEncontradoException::new);
        if (servicio.getEstado() != EstadoServicio.DISPONIBLE) {
            throw new SolicitudNoPermitidaException("Este servicio ya no está disponible.");
        }
        if (servicio.getOfertador().getId().equals(clienteId)) {
            throw new SolicitudNoPermitidaException("No puedes solicitar tu propio servicio.");
        }
        if (solicitudes.existsByClienteIdAndServicioIdAndEstado(clienteId, servicioId, EstadoSolicitud.NEGOCIANDO)) {
            throw new SolicitudNoPermitidaException("Ya tienes una solicitud en negociación para este servicio.");
        }
        int tarifa = tarifaPorHora != null ? tarifaPorHora : servicio.getTarifaPorHora();
        Instant ahora = Instant.now(reloj);
        SolicitudCompra solicitud = solicitudes.save(new SolicitudCompra(cliente, servicio, ahora));
        propuestas.save(new Propuesta(solicitud, cliente, tarifa, servicio.getIntensidadHoraria(), 1, ahora));
        notificador.notificar(servicio.getOfertador(), solicitud, TipoNotificacion.SOLICITUD_RECIBIDA,
                cliente.getNombre() + " quiere contratar «" + servicio.getTitulo() + "» a " + pesos(tarifa)
                        + " por hora (" + servicio.getIntensidadHoraria() + " horas por semana).");
        return solicitud;
    }

    /** COMPRA-CLI-H02: the other party answers with a new rate (and weekly hours if offerer). */
    public SolicitudCompra contraproponer(Integer solicitudId, Integer usuarioId, Integer tarifaPorHora,
            Integer intensidadHoraria) {
        throw new UnsupportedOperationException("Pendiente: COMPRA-CLI-H02");
    }

    /** The counterpart accepts the current proposal: the request becomes ACTIVA. */
    public SolicitudCompra aceptar(Integer solicitudId, Integer usuarioId) {
        throw new UnsupportedOperationException("Pendiente: COMPRA-CLI-H02");
    }

    /**
     * The offerer rejects without a counterproposal: the request becomes CANCELADA, the client is
     * notified and the service stays as it was (still available).
     */
    @Transactional
    public SolicitudCompra rechazar(Integer solicitudId, Integer usuarioId) {
        SolicitudCompra solicitud = solicitudes.findById(solicitudId)
                .orElseThrow(SolicitudNoEncontradaException::new);
        Servicio servicio = solicitud.getServicio();
        if (!servicio.getOfertador().getId().equals(usuarioId)) {
            throw new SolicitudNoPermitidaException("Solo el ofertador puede rechazar la solicitud.");
        }
        solicitud.cancelar(Instant.now(reloj));
        solicitudes.save(solicitud);
        notificador.notificar(solicitud.getCliente(), solicitud, TipoNotificacion.SOLICITUD_RECHAZADA,
                servicio.getOfertador().getNombre() + " rechazó tu solicitud de «" + servicio.getTitulo()
                        + "». El servicio sigue disponible.");
        return solicitud;
    }

    @Transactional(readOnly = true)
    public Optional<SolicitudCompra> buscarPorId(Integer id) {
        return solicitudes.findById(id);
    }

    /** The proposals of a request, oldest round first: the negotiation history. */
    @Transactional(readOnly = true)
    public List<Propuesta> propuestasDe(Integer solicitudId) {
        return propuestas.findBySolicitudIdOrderByNumeroRondaAsc(solicitudId);
    }

    private static String pesos(int valor) {
        return "$" + NumberFormat.getIntegerInstance(COLOMBIA).format(valor) + " COP";
    }
}
