/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

/**
 *
 * @author santi
 */
public class Ubicacion {
    private String ciudad;
    private String municipio;
    
    public Ubicacion(String ciudad, String municipio)
    {
        this.ciudad = ciudad;
        this.municipio = municipio;
    }
    
    public String GetCiudad()
    {
        return ciudad;
    }
    
    public String GetMunicipio()
    {
        return municipio;
    }
}
