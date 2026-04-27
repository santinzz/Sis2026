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
    private List<Servicio> serviciosBrindados;
    private List<Servicio> serviciosRecibidos;
    
    public UsuarioFinal(String nombre, String apellidos, Direccion direccion, String telefonoContacto, String email, String contrasena, String nick, Date fechaAlta) 
    {
        super(nombre, apellidos, direccion, telefonoContacto, email, contrasena, TipoUsuario.USUARIO_FINAL);
        this.nick = nick;
        this.fechaAlta = fechaAlta;
        this.serviciosBrindados = new ArrayList<>();
        this.serviciosRecibidos = new ArrayList<>();
    }
    
    public String GetNick()
    {
        return nick;
    }
    
    public Date GetFechaAlta()
    {
        return fechaAlta;
    }
}
