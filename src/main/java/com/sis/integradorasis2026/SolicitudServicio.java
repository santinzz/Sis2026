package com.sis.integradorasis2026;

import java.util.Date;

public class SolicitudServicio {
    // Declaración de atributos
    private Usuario cliente;
    private Usuario proveedor;
    private Servicio servicio;
    private EstadoSolicitud estado;
    private Date fechaSolicitud;
    private Date fechaCompletado;
    private Pago pago;

    // Constructor para inicializar la solicitud
    public SolicitudServicio(Usuario cliente, Usuario proveedor, Servicio servicio) {
        this.cliente = cliente;
        this.proveedor = proveedor;
        this.servicio = servicio;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.fechaSolicitud = new Date();
    }

    // Métodos de comportamiento
    // Método para aceptar la solicitud
    public void Aceptar()
    {
        if (estado != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden aceptar solicitudes pendientes.");
        }
        estado = EstadoSolicitud.ACEPTADA;
    }

    // Método para iniciar el servicio
    public void Iniciar()
    {
        if (estado != EstadoSolicitud.ACEPTADA) {
            throw new IllegalStateException("Solo se pueden iniciar solicitudes aceptadas.");
        }
        estado = EstadoSolicitud.EN_PROCESO;
    }

    // Método para completar el servicio
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

    // Método para cancelar la solicitud
    public void Cancelar() {
        if (estado == EstadoSolicitud.COMPLETADA) {
            throw new IllegalStateException("No puedes cancelar un servicio completado");
        }
        estado = EstadoSolicitud.CANCELADA;
    }

    // Método para asignar un pago a la solicitud
    public void AsignarPago(Pago pago)
    {
        if (estado != EstadoSolicitud.ACEPTADA)
        {
            throw new IllegalStateException("El pago solo se puede asignar a solicitudes aceptadas.");
        }
        this.pago = pago;
    }

    // Método para verificar si la solicitud tiene un pago asignado
    public boolean TienePago()
    {
        return pago != null;
    }

    // Métodos get
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
