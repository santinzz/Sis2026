package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

public class GestorServicios {
    private List<Servicio> servicios;

    public GestorServicios() {
        this.servicios = new ArrayList<>();
    }

    public void RegistrarServicio(Servicio servicio) {
        this.servicios.add(servicio);
    }

    public List<Servicio> GetServicios() {
        return new ArrayList<>(this.servicios);
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
