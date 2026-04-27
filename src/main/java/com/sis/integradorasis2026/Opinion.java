/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

import java.util.Date;

/**
 *
 * @author santi
 */
public class Opinion {
    String comentario;
    double calificacion;
    Date fecha;
    
    public Opinion(String comentario, double calificacion) {
        this.comentario = comentario; 
        this.calificacion = calificacion; 
        this.fecha = new Date();
    }
}
