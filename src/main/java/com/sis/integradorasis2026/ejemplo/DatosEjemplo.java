package com.sis.integradorasis2026.ejemplo;

import java.util.ArrayList;
import java.util.Arrays;

import com.sis.integradorasis2026.CategoriaServicio;
import com.sis.integradorasis2026.Complejidad;
import com.sis.integradorasis2026.GestorServicios;
import com.sis.integradorasis2026.Horario;
import com.sis.integradorasis2026.Servicio;
import com.sis.integradorasis2026.SistemaSIS;
import com.sis.integradorasis2026.Ubicacion;
import com.sis.integradorasis2026.UsuarioFinal;

public class DatosEjemplo {
    public static void CargarServiciosEjemploSiVacio(GestorServicios gestorServicios, SistemaSIS sistema) {
        if (!gestorServicios.GetServicios().isEmpty()) {
            return;
        }
 
        UsuarioFinal proveedorEjemplo = (UsuarioFinal) sistema.GetGestorUsuarios().BuscarUsuario("santiagogonuz@gmail.com");
 
        gestorServicios.RegistrarServicio(new Servicio(
            "Limpieza de hogar express",
            "Limpieza basica de habitaciones y areas comunes.",
            Arrays.asList(CategoriaServicio.HOGAR),
            new Ubicacion("Monclova", "Centro"),
            120.0,
            Complejidad.BAJA,
            Horario.MANANA,
            "Todo publico",
            4.3,
            new ArrayList<>(),
            proveedorEjemplo
        ));
 
        gestorServicios.RegistrarServicio(new Servicio(
            "Soporte tecnico basico",
            "Revision de equipo, limpieza de software y ajustes de rendimiento.",
            Arrays.asList(CategoriaServicio.TECNOLOGIA),
            new Ubicacion("Monclova", "Industrial"),
            200.0,
            Complejidad.MEDIA,
            Horario.TARDE,
            "Todo publico",
            4.6,
            new ArrayList<>(),
            proveedorEjemplo
        ));
 
        gestorServicios.RegistrarServicio(new Servicio(
            "Clases de matematicas",
            "Asesoria para nivel secundaria y preparatoria.",
            Arrays.asList(CategoriaServicio.EDUCACION),
            new Ubicacion("Monclova", "Universidad"),
            150.0,
            Complejidad.MEDIA_ALTA,
            Horario.MEDIODIA,
            "12+",
            4.8,
            new ArrayList<>(),
            proveedorEjemplo
        ));
 
        gestorServicios.RegistrarServicio(new Servicio(
            "Traslado al aeropuerto",
            "Servicio de traslado seguro con reservacion previa.",
            Arrays.asList(CategoriaServicio.TRANSPORTE),
            new Ubicacion("Monclova", "Norte"),
            300.0,
            Complejidad.BAJA_MEDIA,
            Horario.NOCHE,
            "Todo publico",
            4.2,
            new ArrayList<>(),
            proveedorEjemplo
        ));
 
        gestorServicios.RegistrarServicio(new Servicio(
            "Cobertura fotografica de eventos",
            "Sesion fotografica y edicion basica incluida.",
            Arrays.asList(CategoriaServicio.EVENTOS, CategoriaServicio.TECNOLOGIA),
            new Ubicacion("Monclova", "Centro"),
            500.0,
            Complejidad.ALTA,
            Horario.TARDE,
            "Todo publico",
            4.7,
            new ArrayList<>(),
            proveedorEjemplo
        ));
    }
}
