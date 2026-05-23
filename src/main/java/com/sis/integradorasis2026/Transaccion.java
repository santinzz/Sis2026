package com.sis.integradorasis2026;

import java.util.Date;

public class Transaccion {
    // Declaración de atributos
    private double monto;
    private TipoTransaccion tipo;
    private Date fecha;
    private String concepto;

    // Constructor para inicializar la transacción
    public Transaccion(double monto, TipoTransaccion tipo, String concepto) {
        this.monto = monto;
        this.tipo = tipo;
        this.concepto = concepto;
        this.fecha = new Date();
    }

    // Métodos get
    public double GetMonto() {
        return monto;
    }

    public TipoTransaccion GetTipo() {
        return tipo;
    }

    public Date GetFecha() {
        return fecha;
    }

    public String GetConcepto() {
        return concepto;
    }

    @Override
    // Método toString para visualizar la transacción como una cadena de texto
    public String toString() {
        return String.format("%s | $%.2f | %s", tipo, monto, concepto);
    }
}
