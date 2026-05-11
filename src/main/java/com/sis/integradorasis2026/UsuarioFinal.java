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
public class UsuarioFinal extends Usuario {
    private String nick;
    private Date fechaAlta;
    private Cuenta cuenta;
    private List<Servicio> serviciosBrindados;
    private List<Servicio> serviciosRecibidos;
    private List<Pago> pagosRealizados;
    private List<Pago> pagosRecibidos;
    
    public UsuarioFinal(String nombre, String apellidos, Direccion direccion, String telefonoContacto, String email, String contrasena, String nick, Date fechaAlta) 
    {
        super(nombre, apellidos, direccion, telefonoContacto, email, contrasena, TipoUsuario.USUARIO_FINAL);
        this.nick = nick;
        this.fechaAlta = fechaAlta;
        this.serviciosBrindados = new ArrayList<>();
        this.serviciosRecibidos = new ArrayList<>();
        this.cuenta = new Cuenta();
        this.pagosRealizados = new ArrayList<>();
        this.pagosRecibidos = new ArrayList<>();
    }
    
    public String GetNick()
    {
        return nick;
    }
    
    public Date GetFechaAlta()
    {
        return fechaAlta;
    }

    public Cuenta GetCuenta()
    {
        return cuenta;
    }

    public List<Servicio> GetServiciosBrindados() {
        return new ArrayList<>(serviciosBrindados);
    }

    public List<Servicio> GetServiciosRecibidos() {
        return new ArrayList<>(serviciosRecibidos);
    }

    public void AgregarServicioBrindado(Servicio servicio) {
        if (servicio != null) {
            serviciosBrindados.add(servicio);
        }
    }

    public void RemoverServicioBrindado(Servicio servicio) {
        serviciosBrindados.remove(servicio);
    }

    public void AgregarServicioRecibido(Servicio servicio) {
        if (servicio != null) {
            serviciosRecibidos.add(servicio);
        }
    }

    public List<Pago> GetPagosRealizados() {
        return new ArrayList<>(pagosRealizados);
    }

    public List<Pago> GetPagosRecibidos() {
        return new ArrayList<>(pagosRecibidos);
    }

    public void RegistrarPagoRealizado(Pago pago) {
        if (pago != null) {
            pagosRealizados.add(pago);
        }
    }

    public void RegistrarPagoRecibido(Pago pago) {
        if (pago != null) {
            pagosRecibidos.add(pago);
        }
    }
}
