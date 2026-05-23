/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

public class SistemaSIS {
    // Declaración de atributos
    private GestorUsuarios gestorUsuarios;
    private GestorServicios gestorServicios;
    // Constructor para inicializar el sistema
    public SistemaSIS()
    {
        this.gestorUsuarios = new GestorUsuarios();
        this.gestorServicios = new GestorServicios();
    }
    // Métodos get para acceder a los gestores
    public GestorUsuarios GetGestorUsuarios()
    {
        return gestorUsuarios;
    }
    public GestorServicios GetGestorServicios()
    {
        return gestorServicios;
    }

}
