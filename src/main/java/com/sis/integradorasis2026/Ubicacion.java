/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

public class Ubicacion {
    // Declaración de atributos
    private String ciudad;
    private String municipio;
    
    // Constructor para inicializar la ubicación
    public Ubicacion(String ciudad, String municipio)
    {
        this.ciudad = ciudad;
        this.municipio = municipio;
    }
    
    // Métodos get para acceder a los atributos de la ubicación
    public String GetCiudad()
    {
        return ciudad;
    }
    
    public String GetMunicipio()
    {
        return municipio;
    }

    @Override
    // Método toString para visualizar la ubicación como una cadena de texto
    public String toString() {
        return String.format("%s, %s", municipio, ciudad);
    }
}
