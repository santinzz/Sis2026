/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author santi
 */
public class Opinion {
    private String comentario;
    private double calificacion;
    private Date fecha;
    
    public Opinion(String comentario, double calificacion) {
        this.comentario = comentario; 
        this.calificacion = calificacion; 
        this.fecha = new Date();
    }

    @Override
    public String toString() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String comentarioSeguro = comentario == null ? "(sin comentario)" : comentario;
        return String.format("%.1f/5 - %s (%s)", calificacion, comentarioSeguro, formato.format(fecha));
    }
}
