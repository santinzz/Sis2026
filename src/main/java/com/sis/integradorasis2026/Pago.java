package com.sis.integradorasis2026;

import java.util.Date;

public class Pago {
    private Usuario cliente;
    private Usuario proveedor;
    private Servicio servicio;
    private double monto;
    private Date fecha;
    private EstadoPago estado;

    public Pago(Usuario cliente, Usuario proveedor, Servicio servicio, double monto) {
        this.cliente = cliente;
        this.proveedor = proveedor;
        this.servicio = servicio;
        this.monto = monto;
        this.fecha = new Date();
        this.estado = EstadoPago.PENDIENTE;
    }

    public void Completar()
    {
        this.estado = EstadoPago.COMPLETADO;
    }
    
    public double GetMonto()
    {
        return monto;
    }
}
