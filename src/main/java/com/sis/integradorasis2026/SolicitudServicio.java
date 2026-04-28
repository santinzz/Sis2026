package com.sis.integradorasis2026;

import java.util.Date;

public class SolicitudServicio {
    private Usuario cliente;
    private Usuario proveedor;
    private Servicio servicio;

    private EstadoSolicitud estado;
    private Date fechaSolicitud;
    private Date fechaCompletado;

    private Pago pago;

    public SolicitudServicio(Usuario cliente, Usuario proveedor, Servicio servicio) {
        this.cliente = cliente;
        this.proveedor = proveedor;
        this.servicio = servicio;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.fechaSolicitud = new Date();
    }

    public void Aceptar()
    {
        if (estado != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden aceptar solicitudes pendientes.");
        }
        estado = EstadoSolicitud.ACEPTADA;
    }

    public void Iniciar()
    {
        if (estado != EstadoSolicitud.ACEPTADA) {
            throw new IllegalStateException("Solo se pueden iniciar solicitudes aceptadas.");
        }
        estado = EstadoSolicitud.EN_PROCESO;
    }

    public void Completar() {
        if (estado != EstadoSolicitud.EN_PROCESO) {
            throw new IllegalStateException("Debe estar en proceso");
        }
        if (pago == null) {
            pago = new Pago(cliente, proveedor, servicio, servicio.GetPrecioHora());
        }
        pago.Completar();
        estado = EstadoSolicitud.COMPLETADA;
        fechaCompletado = new Date();
    }

    public void Cancelar() {
        if (estado == EstadoSolicitud.COMPLETADA) {
            throw new IllegalStateException("No puedes cancelar un servicio completado");
        }
        estado = EstadoSolicitud.CANCELADA;
    }

    public void AsignarPago(Pago pago)
    {
        if (estado != EstadoSolicitud.ACEPTADA)
        {
            throw new IllegalStateException("El pago solo se puede asignar a solicitudes aceptadas.");
        }
        this.pago = pago;
    }

    public boolean TienePago()
    {
        return pago != null;
    }

    public Usuario GetCliente() {
        return cliente;
    }

    public Usuario GetProveedor() {
        return proveedor;
    }

    public Servicio GetServicio() {
        return servicio;
    }

    public EstadoSolicitud GetEstado() {
        return estado;
    }

    public Date GetFechaSolicitud() {
        return fechaSolicitud;
    }

    public Date GetFechaCompletado() {
        return fechaCompletado;
    }

    public Pago GetPago() {
        return pago;
    }
}
