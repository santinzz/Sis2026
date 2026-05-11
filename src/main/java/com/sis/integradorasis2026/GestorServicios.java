package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

public class GestorServicios {
    private List<Servicio> servicios;

    public GestorServicios() {
        this.servicios = new ArrayList<>();
    }

    public void RegistrarServicio(Servicio servicio) {
        if (servicio != null) {
            this.servicios.add(servicio);
        }
    }

    public List<Servicio> GetServicios() {
        return new ArrayList<>(this.servicios);
    }

    public List<Servicio> GetServiciosPorEstado(EstadoServicio estado) {
        List<Servicio> filtrados = new ArrayList<>();
        for (Servicio servicio : servicios) {
            if (servicio.GetEstadoPublicacion() == estado) {
                filtrados.add(servicio);
            }
        }
        return filtrados;
    }

    public List<Servicio> GetServiciosAprobados() {
        return GetServiciosPorEstado(EstadoServicio.APROBADO);
    }

    public List<Servicio> GetServiciosPendientes() {
        return GetServiciosPorEstado(EstadoServicio.PENDIENTE);
    }

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

    public Servicio BuscarServicio(String nombre) {
        for (Servicio servicio : servicios) {
            if (servicio.GetNombre().equalsIgnoreCase(nombre)) {
                return servicio;
            }
        }
        return null;
    }
    
}
