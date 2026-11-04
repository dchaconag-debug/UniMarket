package com.atenea.unimarket.negocio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atenea.unimarket.datos.modelo.Notificacion;
import com.atenea.unimarket.datos.repositorio.NotificacionRepository;

@Service
public class NotificacionService {

    private final NotificacionRepository notificaciones;

    public NotificacionService(NotificacionRepository notificaciones) {
        this.notificaciones = notificaciones;
    }

    /** The user's notifications, newest first. */
    @Transactional(readOnly = true)
    public List<Notificacion> deUsuario(Integer usuarioId) {
        return notificaciones.findByUsuarioIdOrderByFechaCreacionDescIdDesc(usuarioId);
    }
}
