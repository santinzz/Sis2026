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
    
    public String GetCalle()
    {
        return calle;
    }
    
    public void SetCalle(String calle)
    {
        this.calle = calle;
    }
    
    public int GetNumero()
    {
        return numero;
    }
    
    public void SetNumero(int numero) 
    {
        this.numero = numero;
    }
    
    public String GetCiudad()
    {
        return ciudad;
    }
    
    public void SetCiudad(String ciudad)
    {
        this.ciudad = ciudad;
    }
    
    public String GetEstado()
    {
        return estado;
    }
    
    public void SetEstado(String estado)
    {
        this.estado = estado;
    }
    
    public int GetCodigoPostal()
    {
        return codigoPostal;
    }
    
    public void SetCodigoPostal(int codigoPostal)
    {
        this.codigoPostal = codigoPostal;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s %d, %s, %s %d", calle, numero, ciudad, estado, codigoPostal);
    }
}