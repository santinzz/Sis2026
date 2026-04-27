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
public class Servicio {
    private String nombre;
    private String descripcion;
    private List<String> tipos;
    private Ubicacion ubicacion;
    private double precioHora;
    private Complejidad complejidad;
    private Horario horarioRealizacion;
    private String edadRecomendada;
    private double calificacionPromedio;
    private List<Opinion> opiniones;
    
    public Servicio(String nombre, double precioHora, Complejidad complejidad, Ubicacion ubicacion, Horario horarioRealizacion, String edadRecomendada) {
        this.nombre = nombre; 
        this.precioHora = precioHora; 
        this.complejidad = complejidad; 
        this.ubicacion = ubicacion;
        this.tipos = new ArrayList<>();
        this.opiniones = new ArrayList<>();
        this.horarioRealizacion = horarioRealizacion;
        this.edadRecomendada = edadRecomendada;
    }
}
