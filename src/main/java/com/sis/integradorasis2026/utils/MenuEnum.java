package com.sis.integradorasis2026.utils;

import java.util.Scanner;

public class MenuEnum {
    public static <T extends Enum<T>> T MostrarMenuEnum(Class<T> enumClass, Scanner lector)
    {
        T[] valores = enumClass.getEnumConstants();

        Menu menu = new Menu(50)
                .Titulo("Seleccione una opcion");

        for (int i = 0; i < valores.length; i++) {
            menu.AgregarCampo(valores[i]);
        }

        menu.Peticion("Ingrese una opcion:= ");

        int opcion = menu.MostrarYLeer(lector);

        if (opcion < 1 || opcion > valores.length) {
            System.out.println("Opcion invalida. Se seleccionara la primera opcion por defecto.");
            return valores[0];
        }
        return valores[opcion - 1];
    }
}
