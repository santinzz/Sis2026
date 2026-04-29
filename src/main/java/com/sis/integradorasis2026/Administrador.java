/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

/**
 *
 * @author santi
 */
public class Administrador extends Usuario {
    public Administrador(String nombre, String apellidos, Direccion direccion, String telefonoContacto, String email, String contrasena) {
        super(nombre, apellidos, direccion, telefonoContacto, email, contrasena, TipoUsuario.ADMINISTRADOR);
    }
}
