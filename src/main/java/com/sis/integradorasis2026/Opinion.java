/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Opinion {
    // Declaración de atributos
    private String comentario;
    private double calificacion;
    private Date fecha;
    private String autorEmail;
    private List<String> evidencias;
    
    // Constructor para inicializar la opinión
    public Opinion(String comentario, double calificacion) {
        this(comentario, calificacion, null, new ArrayList<>());
    }

    // Constructor para inicializar la opinión con autor y evidencias
    public Opinion(String comentario, double calificacion, String autorEmail, List<String> evidencias) {
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.autorEmail = autorEmail;
        this.evidencias = evidencias == null ? new ArrayList<>() : new ArrayList<>(evidencias);
        this.fecha = new Date();
    }

    // Métodos get
    public String GetAutorEmail() {
        return autorEmail;
    }

    public double GetCalificacion() {
        return calificacion;
    }

    public List<String> GetEvidencias() {
        return new ArrayList<>(evidencias);
    }

    @Override
    // Método toString para mostrar la opinión de forma legible
    public String toString() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String comentarioSeguro = comentario == null ? "(sin comentario)" : comentario;
        String autor = autorEmail == null ? "anonimo" : autorEmail;
        String evidenciaInfo = evidencias.isEmpty() ? "sin evidencia" : "evidencias: " + evidencias.size();
        return String.format("%.1f/5 - %s (%s) [%s, %s]", calificacion, comentarioSeguro, formato.format(fecha), autor, evidenciaInfo);
    }
}
