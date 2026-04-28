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
    private GestorUsuarios gestorUsuarios;
    private GestorServicios gestorServicios;
    
    public SistemaSIS()
    {
        this.gestorUsuarios = new GestorUsuarios();
        this.gestorServicios = new GestorServicios();
    }

    public GestorUsuarios GetGestorUsuarios()
    {
        return gestorUsuarios;
    }

    public GestorServicios GetGestorServicios()
    {
        return gestorServicios;
    }

}
