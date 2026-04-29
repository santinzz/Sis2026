package com.sis.integradorasis2026;

public enum CategoriaServicio {
    MECANICA,
    CARPINTERIA,
    PLOMERIA,
    BELLEZA,

    TECNOLOGIA,
    HOGAR,
    EDUCACION,  
    TRANSPORTE, 
    EVENTOS,        
    SALUD_Y_BIENESTAR,
    LEGAL_Y_FINANZAS,
    OTROS;

    @Override
    public String toString() {
        String nombre = name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1);
    }
}
