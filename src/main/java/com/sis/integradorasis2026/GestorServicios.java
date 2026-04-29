package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

import com.sis.integradorasis2026.utils.Normalizamiento;

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

    public static List<Servicio> FiltrarServiciosPorCategoria(List<Servicio> servicios, CategoriaServicio categoria) {
        List<Servicio> filtrados = new ArrayList<>();
 
        for (Servicio servicio : servicios) {
            for (CategoriaServicio tipo : servicio.GetTipos()) {
                if (tipo == categoria) {
                    filtrados.add(servicio);
                    break;
                }
            }
        }
 
        return filtrados;
    }
    
}
