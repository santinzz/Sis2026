/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sis.integradorasis2026.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author santi
 */
public class Menu {
    private final List<String> campos = new ArrayList<>();
    private final int ancho;
    private String titulo;
    private String peticion;

    public Menu(int ancho) {
        if (ancho < 10)
            throw new IllegalArgumentException("Ancho minimo de 10");
        this.ancho = ancho;
    }

    public Menu() {
        this.ancho = 40;
    }

    public Menu Titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public Menu AgregarCampo(Object campo) 
    {
        if (campo == null) {
            throw new IllegalArgumentException("El campo no puede ser nulo");
        }

        if (campo instanceof String || campo instanceof Enum<?>) {
            campos.add(campo.toString());
        } else {
            throw new IllegalArgumentException("Solo se permiten String o Enum");
        }

        return this;
    }

    public Menu Peticion(String peticion)
    {
        this.peticion = peticion;
        return this;
    }

    public String Construir(boolean esMenuDeOpciones) 
    {
        StringBuilder menuString = new StringBuilder();

        menuString.append("*".repeat(ancho)).append("\n");

        if (titulo != null && !titulo.isEmpty()) {
            for (String linea : Wrap(titulo, ancho - 2)) {
                menuString
                        .append("*")
                        .append(Centrar(linea, ancho - 2))
                        .append("*\n");
            }

            menuString
                    .append("*".repeat(ancho)).append("\n");
        }

        for (int i = 0; i < campos.size(); i++) {
            String prefijo = esMenuDeOpciones ? (i + 1) + ". " : "";
            int contenidoAncho = ancho - 2;

            List<String> lineas = Wrap(campos.get(i), contenidoAncho - prefijo.length());

            for (int j = 0; j < lineas.size(); j++) {
                if (j == 0) {
                    menuString.append("*")
                            .append(PadDerecha(prefijo + lineas.get(j), contenidoAncho))
                            .append("*\n");
                } else {
                    menuString.append("*")
                            .append(PadDerecha(" ".repeat(prefijo.length()) + lineas.get(j), contenidoAncho))
                            .append("*\n");
                }
            }
        }

        menuString.append("*".repeat(ancho));

        return menuString.toString();
    }

    private static String PadDerecha(String s, int ancho) 
    {
        if (s.length() > ancho)
            return s.substring(0, ancho);
        return s + " ".repeat(ancho - s.length());
    }

    private static String Centrar(String s, int ancho) 
    {
        if (s.length() > ancho)
            return s.substring(0, ancho);
        int espacios = ancho - s.length();
        int izq = espacios / 2;
        int der = espacios - izq;
        return " ".repeat(izq) + s + " ".repeat(der);
    }

    private static List<String> Wrap(String texto, int ancho) 
    {
        List<String> lineas = new ArrayList<>();
        String[] palabras = texto.split(" ");
        StringBuilder actual = new StringBuilder();

        for (String palabra : palabras) {
            if (actual.length() == 0) {
                actual.append(palabra);
            } else if (actual.length() + 1 + palabra.length() <= ancho) {
                actual.append(" ").append(palabra);
            } else {
                lineas.add(actual.toString());
                actual = new StringBuilder(palabra);
            }
        }

        if (!actual.isEmpty()) {
            lineas.add(actual.toString());
        }

        return lineas;
    }

    public int MostrarYLeer(Scanner lector) {
        while (true) {
            System.out.println(Construir(true));
            System.out.print(peticion);

            if (lector.hasNextInt()) {
                int op = Integer.parseInt(lector.nextLine());
                if (op >= 1 && op <= campos.size()) {
                    return op;
                } else {
                    lector.next();
                }
            }
            System.out.println("Opcion invalida\n");
        }
    }
}
