/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.Date;
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
    private Usuario proveedor;
    private Usuario creador;
    private EstadoServicio estadoPublicacion;
    private Date fechaPublicacion;
   
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
        this.creador = proveedor;
        this.estadoPublicacion = EstadoServicio.PENDIENTE;
        this.fechaPublicacion = new Date();
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
 
    public List<String> GetTipos()
    {
        return tipos;
    }
 
    public void SetTipos(List<String> tipos)
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
        RecalcularCalificacion();
    }

    public void AgregarOpinion(Opinion opinion)
    {
        if (opinion != null) {
            opiniones.add(opinion);
            RecalcularCalificacion();
        }
    }

    private void RecalcularCalificacion()
    {
        if (opiniones.isEmpty()) {
            calificacionPromedio = 0.0;
            return;
        }
        double suma = 0.0;
        for (Opinion opinion : opiniones) {
            suma += opinion == null ? 0.0 : opinion.GetCalificacion();
        }
        calificacionPromedio = suma / opiniones.size();
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

    public Usuario GetCreador() {
        return creador;
    }

    public void SetCreador(Usuario creador) {
        this.creador = creador;
    }

    public EstadoServicio GetEstadoPublicacion() {
        return estadoPublicacion;
    }

    public void SetEstadoPublicacion(EstadoServicio estadoPublicacion) {
        this.estadoPublicacion = estadoPublicacion;
    }

    public void Aprobar() {
        this.estadoPublicacion = EstadoServicio.APROBADO;
    }

    public void Rechazar() {
        this.estadoPublicacion = EstadoServicio.RECHAZADO;
    }

    public Date GetFechaPublicacion() {
        return fechaPublicacion;
    }
    
    public String InfoResumida()
    {
        String estadoExtra = estadoPublicacion == EstadoServicio.APROBADO ? "" : " | " + estadoPublicacion;
        return String.format(
            "%s | $%.2f/hora | %s (%.1f) | %s%s",
            nombre,
            precioHora,
            generarEstrellas(),
            calificacionPromedio,
            complejidad,
            estadoExtra
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
        cadena += "├─ Tipos: " + (tipos.isEmpty() ? "No especificados" : String.join(", ", tipos)) + "\n";
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
        cadena += "\nESTADO PUBLICACION:\n";
        cadena += "└─ " + estadoPublicacion + "\n";
        cadena += "\n" + "─".repeat(70) + "\n";
        return cadena;
    }
}