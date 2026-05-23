/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    //Declaración de atributos para la clase Cuenta
    private double saldo;
    private List<Transaccion> historialTransacciones;

    // Constructor para inicializar la cuenta con saldo cero y un historial de transacciones vacío
    public Cuenta()
    {
        this.saldo = 0.0;
        this.historialTransacciones = new ArrayList<>();
    }

    // Métodos de comportamiento para la clase Cuenta
    // Método para depositar dinero en la cuenta
    public void Depositar(double monto)
    {
        if (monto <= 0) throw new IllegalArgumentException("El monto a depositar debe ser mayor a cero.");
        saldo += monto;
        historialTransacciones.add(new Transaccion(monto, TipoTransaccion.DEPOSITO, "Deposito"));
    }

    // Método para retirar dinero de la cuenta
    public void Retirar(double monto)
    {
        if (monto <= 0) throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero.");
        if (monto > saldo) throw new IllegalArgumentException("Fondos insuficientes para realizar el retiro.");
        saldo -= monto;
        historialTransacciones.add(new Transaccion(monto, TipoTransaccion.RETIRO, "Retiro"));
    }

    // Método para registrar un pago de salida (gasto)
    public void RegistrarPagoSalida(double monto, String concepto)
    {
        if (monto <= 0) throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        if (monto > saldo) throw new IllegalArgumentException("Saldo insuficiente para el pago.");
        saldo -= monto;
        historialTransacciones.add(new Transaccion(monto, TipoTransaccion.PAGO, concepto));
    }

    // Método para registrar un pago de entrada (ingreso)
    public void RegistrarPagoEntrada(double monto, String concepto)
    {
        if (monto <= 0) throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        saldo += monto;
        historialTransacciones.add(new Transaccion(monto, TipoTransaccion.PAGO, concepto));
    }

    //Metodo Get Saldo
    public double GetSaldo()
    {
        return saldo;
    }

    // Método Get transacciones de la cuenta
    public List<Transaccion> GetHistorialTransacciones()
    {
        return new ArrayList<>(historialTransacciones);
    }
}