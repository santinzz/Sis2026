/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026;

/**
 *
 * @author santi
 */
public class Direccion {
    private String calle;
    private int numero;
    private String ciudad;
    private String estado;
    private int codigoPostal;
    
    public Direccion(String calle, int numero, String ciudad, String estado, int codigoPostal)
    {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
    }
}
