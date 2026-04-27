/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author santi
 */
public class SistemaSIS {
    private List<Usuario> usuarios;
    private List<Servicio> servicios;
    
    public SistemaSIS()
    {
        this.usuarios = new ArrayList<>();
    }
    
    public void RegistrarUsuario(Usuario usuario) 
    {
        usuarios.add(usuario);
        System.out.println("Usuario registrado correctamente: " + usuario.GetEmail());
    }
    
    public void EliminarUsuario(String email)
    {
        if (usuarios.removeIf(usuario -> usuario.GetEmail().equals(email))) {
            System.out.println("Usuario con correo: " + email + " eliminado exitosamente.");
        } else {
            System.out.println("Usuario con correo: " + email + " no encontrado");
        }
    }
    
    public Usuario BuscarUsuario(String email) 
    {
        for (Usuario usuario : usuarios) 
        {
            if (usuario.GetEmail().equals(email)) 
            {
                return usuario;
            }
        }
        return null;
    }
    
    public void IniciarSesion(String email, String contrasena) {
        for (Usuario usuario : usuarios)
        {
            if (usuario.Autenticar(email, contrasena)) 
            {
                if (usuario instanceof Administrador) 
                {
                    System.out.println("Acceso concedido: PANEL DE CONTROL ADMINISTRADOR");
                } 
                else 
                {
                    System.out.println("Acceso concedido: PANEL DE USUARIO");
                }
                Sesion.GetInstancia().Login(usuario);
                return;
            }
        }
        System.out.println("Credenciales no validas");
    }
    
    public void CerrarSesion() {
        if (Sesion.GetInstancia().EstaLogeado()) {
            Sesion.GetInstancia().Logout();
        } else {
            throw new IllegalStateException("No hay una sesion activa para cerrar");
        }
    }
}
