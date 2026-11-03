package com.atenea.unimarket.negocio;

import org.springframework.stereotype.Component;

/** Placeholder until SolicitudCompra exists (COMPRA-CLI). Replace the body with a repository query. */
@Component
class NegociacionesEnCursoPendiente implements NegociacionesEnCurso {

    @Override
    public boolean hayParaServicio(Integer servicioId) {
        // TODO: true if the service has a SolicitudCompra in NEGOCIANDO or ACTIVA.
        return false;
    }
}
