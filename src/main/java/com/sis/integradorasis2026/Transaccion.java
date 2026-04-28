package com.sis.integradorasis2026;

import java.util.Date;

public class Transaccion {
    private double monto;
    private TipoTransaccion tipo;
    private Date fecha;
    private String concepto;

    public Transaccion(double monto, TipoTransaccion tipo, String concepto) {
        this.monto = monto;
        this.tipo = tipo;
        this.concepto = concepto;
        this.fecha = new Date();
    }
}
