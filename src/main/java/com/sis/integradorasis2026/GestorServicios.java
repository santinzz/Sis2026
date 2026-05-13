package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

public class GestorServicios {
    // Declaración de atributos
    private List<Servicio> servicios;

    // Constructor para inicializar el gestor de servicios
    public GestorServicios() {
        this.servicios = new ArrayList<>();
    }

    // Métodos de comportamiento
    // Método para registrar un nuevo servicio en el gestor de servicios
    public void RegistrarServicio(Servicio servicio) {
        if (servicio != null) {
            this.servicios.add(servicio);
        }
    }

    // Método para obtener la lista de servicios registrados en el gestor de servicios
    public List<Servicio> GetServicios() {
        return new ArrayList<>(this.servicios);
    }

    // Método para obtener la lista de servicios filtrados por estado
    public List<Servicio> GetServiciosPorEstado(EstadoServicio estado) {
        List<Servicio> filtrados = new ArrayList<>();
        for (Servicio servicio : servicios) {
            if (servicio.GetEstadoPublicacion() == estado) {
                filtrados.add(servicio);
            }
        }
        return filtrados;
    }

    // Métodos para obtener servicios por estado específico
    public List<Servicio> GetServiciosAprobados() {
        return GetServiciosPorEstado(EstadoServicio.APROBADO);
    }

    public List<Servicio> GetServiciosPendientes() {
        return GetServiciosPorEstado(EstadoServicio.PENDIENTE);
    }

    // Métodos para aprobar, rechazar o eliminar un servicio
    public void AprobarServicio(Servicio servicio) {
        if (servicio != null) {
            servicio.Aprobar();
        }
    }

    public void RechazarServicio(Servicio servicio) {
        if (servicio != null) {
            servicio.Rechazar();
        }
    }

    public void EliminarServicio(Servicio servicio) {
        this.servicios.remove(servicio);
    }

    // Método para buscar un servicio por su nombre
    public Servicio BuscarServicio(String nombre) {
        for (Servicio servicio : servicios) {
            if (servicio.GetNombre().equalsIgnoreCase(nombre)) {
                return servicio;
            }
        }
        return null;
    }
    
}
