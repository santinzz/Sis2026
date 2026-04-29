package com.sis.integradorasis2026.utils;

public class Normalizamiento {
    public static String NormalizarCategoria(String categoria) {
        if (categoria == null) {
            return "";
        }
        return categoria.trim().toLowerCase();
    }
}
