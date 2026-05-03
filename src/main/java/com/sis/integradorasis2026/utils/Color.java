/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.sis.integradorasis2026.utils;

/**
 *
 * @author Admin
 */
public enum Color {
    // \033 is the octal representation of the Escape character (ASCII 27)
    RESET("\033[0m"),
    RED("\033[31m"),
    GREEN("\033[32m"),
    YELLOW("\033[33m"),
    BLUE("\033[34m"),
    PURPLE("\033[35m"),
    CYAN("\033[36m"),
    WHITE("\033[37m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static String colorize(String text, Color color) {
        return color.getCode() + text + Color.RESET.getCode();
    }
}