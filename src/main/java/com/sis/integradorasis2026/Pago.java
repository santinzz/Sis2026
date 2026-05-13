package com.sis.integradorasis2026;

import java.util.Date;

public class Pago {
    // Declaración de atributos
    private Usuario cliente;
    private Usuario proveedor;
    private Servicio servicio;
    private double monto;
    private Date fecha;
    private EstadoPago estado;

    // Constructor para inicializar el pago
    public Pago(Usuario cliente, Usuario proveedor, Servicio servicio, double monto) {
        this.cliente = cliente;
        this.proveedor = proveedor;
        this.servicio = servicio;
        this.monto = monto;
        this.fecha = new Date();
        this.estado = EstadoPago.PENDIENTE;
    }

    // Métodos de comportamiento
    public void Completar()
    {
        this.estado = EstadoPago.COMPLETADO;
    }

    public void Cancelar()
    {
        this.estado = EstadoPago.CANCELADO;
    }
    
    // Métodos get
    public double GetMonto()
    {
        return monto;
    }

    public Usuario GetCliente()
    {
        return cliente;
    }

    public Usuario GetProveedor()
    {
        return proveedor;
    }

    public Servicio GetServicio()
    {
        return servicio;
    }

    public Date GetFecha()
    {
        return fecha;
    }

    public EstadoPago GetEstado()
    {
        return estado;
    }

    @Override
    // Método toString para mostrar el pago de forma legible
    public String toString() {
        return String.format("%s | $%.2f | %s", estado, monto, servicio == null ? "Servicio" : servicio.GetNombre());
    }
}
