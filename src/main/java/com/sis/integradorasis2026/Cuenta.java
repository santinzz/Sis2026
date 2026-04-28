/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author santi
 */
public class Cuenta {
    private double saldo;
    private List<Transaccion> historialTransacciones;

    public Cuenta()
    {
        this.saldo = 0.0;
        this.historialTransacciones = new ArrayList<>();
    }

    public void Depositar(double monto)
    {
        if (monto <= 0) throw new IllegalArgumentException("El monto a depositar debe ser mayor a cero.");
        saldo += monto;
        historialTransacciones.add(new Transaccion(monto, TipoTransaccion.DEPOSITO, "Deposito"));
    }

    public void Retirar(double monto)
    {
        if (monto <= 0) throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero.");
        if (monto > saldo) throw new IllegalArgumentException("Fondos insuficientes para realizar el retiro.");
        saldo -= monto;
        historialTransacciones.add(new Transaccion(monto, TipoTransaccion.RETIRO, "Retiro"));
    }

    public double GetSaldo()
    {
        return saldo;
    }

    public List<Transaccion> GetHistorialTransacciones()
    {
        return historialTransacciones;
    }
}