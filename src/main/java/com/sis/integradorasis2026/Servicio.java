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
    private List<CategoriaServicio> tipos;
    private Ubicacion ubicacion;
    private double precioHora;
    private Complejidad complejidad;
    private Horario horarioRealizacion;
    private String edadRecomendada;
    private double calificacionPromedio;
        private List<Opinion> opiniones;
        private Usuario proveedor;
   
    public Servicio(String nombre, double precioHora, Complejidad complejidad, Ubicacion ubicacion, Horario horarioRealizacion, String edadRecomendada, Usuario proveedor) {
        this.nombre = nombre;
        this.precioHora = precioHora;
        this.complejidad = complejidad;
        this.ubicacion = ubicacion;
        this.tipos = new ArrayList<>();
        this.opiniones = new ArrayList<>();
        this.horarioRealizacion = horarioRealizacion;
        this.edadRecomendada = edadRecomendada;
        this.proveedor = proveedor;
    }

    // Todos los atributos constructor
    public Servicio(String nombre, String descripcion, List<CategoriaServicio> tipos, Ubicacion ubicacion, double precioHora, Complejidad complejidad, Horario horarioRealizacion, String edadRecomendada, double calificacionPromedio, List<Opinion> opiniones, Usuario proveedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipos = tipos != null ? tipos : new ArrayList<>();
        this.ubicacion = ubicacion;
        this.precioHora = precioHora;
        this.complejidad = complejidad;
        this.horarioRealizacion = horarioRealizacion;
        this.edadRecomendada = edadRecomendada;
        this.calificacionPromedio = calificacionPromedio;
        this.opiniones = opiniones != null ? opiniones : new ArrayList<>();
        this.proveedor = proveedor;
    }
 
    public String GetNombre()
    {
        return nombre;
    }
 
    public void SetNombre(String nombre)
    {
        this.nombre = nombre;
    }
 
    public String GetDescripcion()
    {
        return descripcion;
    }
 
    public void SetDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
 
    public List<CategoriaServicio> GetTipos()
    {
        return tipos;
    }
 
    public void SetTipos(List<CategoriaServicio> tipos)
    {
        this.tipos = tipos;
    }
 
    public Ubicacion GetUbicacion()
    {
        return ubicacion;
    }
 
    public void SetUbicacion(Ubicacion ubicacion)
    {
        this.ubicacion = ubicacion;
    }
 
    public double GetPrecioHora()
    {
        return precioHora;
    }
 
    public void SetPrecioHora(double precioHora)
    {
        this.precioHora = precioHora;
    }
 
    public Complejidad GetComplejidad()
    {
        return complejidad;
    }
 
    public void SetComplejidad(Complejidad complejidad)
    {
        this.complejidad = complejidad;
    }
 
    public Horario GetHorarioRealizacion()
    {
        return horarioRealizacion;
    }
 
    public void SetHorarioRealizacion(Horario horarioRealizacion)
    {
        this.horarioRealizacion = horarioRealizacion;
    }
 
    public String GetEdadRecomendada()
    {
        return edadRecomendada;
    }
 
    public void SetEdadRecomendada(String edadRecomendada)
    {
        this.edadRecomendada = edadRecomendada;
    }
 
    public double GetCalificacionPromedio()
    {
        return calificacionPromedio;
    }
 
    public void SetCalificacionPromedio(double calificacionPromedio)
    {
        this.calificacionPromedio = calificacionPromedio;
    }
 
    public List<Opinion> GetOpiniones()
    {
        return opiniones;
    }
 
    public void SetOpiniones(List<Opinion> opiniones)
    {
        this.opiniones = opiniones;
    }
 
    private String generarEstrellas()
    {
        StringBuilder estrellas = new StringBuilder();
        int estrellasLlenas = (int) calificacionPromedio;
        int media = (calificacionPromedio % 1 >= 0.5) ? 1 : 0;
        int estrellasVacias = 5 - estrellasLlenas - media;

        for (int i = 0; i < estrellasLlenas; i++) {
            estrellas.append("*");
        }
        if (media == 1) {
            estrellas.append("+");
        }
        for (int i = 0; i < estrellasVacias; i++) {
            estrellas.append("-");
        }

        return estrellas.toString();
    }

    public Usuario GetProveedor() {
        return proveedor;
    }
    
    public String InfoResumida()
    {
        return String.format(
            "%s | $%.2f/hora | %s (%.1f) | %s",
            nombre,
            precioHora,
            generarEstrellas(),
            calificacionPromedio,
            complejidad
        );
    }
    
    public String InfoAll()
    {
        String cadena = "";

        cadena += "┌" + "─".repeat(68) + "┐\n";
        cadena += String.format("│ %-66s │%n", "INFORMACION DEL SERVICIO");
        cadena += "└" + "─".repeat(68) + "┘\n";

        cadena += "\nDETALLES BASICOS:\n";
        cadena += "├─ Nombre: " + nombre + "\n";
        cadena += "├─ Descripción: " + (descripcion != null ? descripcion : "No disponible") + "\n";
        cadena += "├─ Tipos: " + (tipos.isEmpty() ? "No especificados" : tipos.toString()) + "\n";
        cadena += "└─ Edad Recomendada: " + edadRecomendada + "\n";
        cadena += "\nPRECIOS Y COMPLEJIDAD:\n";
        cadena += String.format("├─ Precio por hora: $%.2f%n", precioHora);
        cadena += "└─ Nivel de Complejidad: " + complejidad + "\n";
        cadena += "\nUBICACION Y HORARIO:\n";
        if (ubicacion != null) {
            cadena += "├─ " + ubicacion.toString() + "\n";
        }
        if (horarioRealizacion != null) {
            cadena += "└─ " + horarioRealizacion.toString() + "\n";
        }

        cadena += "\nCALIFICACION:\n";
        cadena += String.format("├─ Estrellas: %s%n", generarEstrellas());  
        cadena += String.format("└─ Promedio: %.1f/5.0 (%d opiniones)%n", 
            calificacionPromedio, opiniones.size());
        if (!opiniones.isEmpty()) {
            cadena += "\nOPINIONES:\n";
            for (Opinion opinion : opiniones) {
                cadena += "├─ " + opinion.toString() + "\n";
            }
        }
        cadena += "\n" + "─".repeat(70) + "\n";
        return cadena;
    }
}