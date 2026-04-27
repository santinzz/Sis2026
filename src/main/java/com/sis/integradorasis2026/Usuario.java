/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

abstract public class Usuario {
    private String nombre;
    private String apellidos;
    private Direccion direccion;
    private String telefonoContacto;
    private String email;
    private String contrasena;
    private TipoUsuario tipoUsuario;
    
    public Usuario(String nombre, String apellidos, Direccion direccion, String telefonoContacto, String email, String contrasena, TipoUsuario tipoUsuario) 
    {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefonoContacto = telefonoContacto;
        this.email = email;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    } 
    
    public String GetNombre()
    {
        return nombre;
    }
    
    public void SetNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public String GetApellidos()
    {
        return apellidos;
    }
    
    public void SetApellidos()
    {
        this.apellidos = apellidos;
    }
    
    public Direccion GetDireccion()
    {
        return direccion;
    }
    
    public void SetDireccion()
    {
        this.direccion = direccion;
    }
    
    public String GetTelefonoContacto()
    {
        return telefonoContacto;
    }
    
    public void SetTelefonoContacto(String telefonoContacto) 
    {
        this.telefonoContacto = telefonoContacto;
    }
    
    public String GetEmail()
    {
        return email;
    }
    
    public void SetEmail(String email)
    {
        this.email = email;
    }
    
    public boolean CompararContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }
    
    public TipoUsuario GetTipoUsuario()
    {
        return tipoUsuario;
    }
    
    public void SetTipoUsuario()
    {
        this.tipoUsuario = tipoUsuario;
    }
    
    public boolean Autenticar(String email, String contrasena) 
    {
        boolean esEmailCorrecto = GetEmail().equals(email);
        return esEmailCorrecto && CompararContrasena(contrasena);
    }
}
