/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

public class Sesion {
    //Declaración de atributos
    private static Sesion instancia;
    private Usuario usuarioActual;
    // Constructor privado para evitar instanciación directa
    private Sesion() {}
    // Método para obtener la instancia única de Sesion
    public static Sesion GetInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }
    // Métodos de comportamiento
    // Método para iniciar sesión con un usuario dado
    public void Login(Usuario usuario)
    {
        usuarioActual = usuario;
    }
    // Método para cerrar sesión
    public void Logout()
    {
        usuarioActual = null;
    }
    // Método para obtener el usuario actualmente logeado
    public Usuario GetUsuarioActual()
    {
        return usuarioActual;
    }
    // Método para verificar si hay un usuario logeado
    public boolean EstaLogeado() 
    {
        return usuarioActual != null;
    }
}
