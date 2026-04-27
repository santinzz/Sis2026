/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

/**
 *
 * @author santi
 */
public class Sesion {
    private static Sesion instancia;
    
    private Usuario usuarioActual;
    
    private Sesion() {}
    
    public static Sesion GetInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }
    
    public void Login(Usuario usuario)
    {
        usuarioActual = usuario;
    }
    
    public void Logout()
    {
        usuarioActual = null;
    }
    
    public Usuario GetUsuarioActual()
    {
        return usuarioActual;
    }
    
    public boolean EstaLogeado() 
    {
        return usuarioActual != null;
    }
}
