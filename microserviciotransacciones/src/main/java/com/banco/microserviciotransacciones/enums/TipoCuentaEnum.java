/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.enums;

/**
 *
 * @author Nancy Mejia
 */

public enum TipoCuentaEnum {
    Corriente("Corriente"),
    Ahorros("Ahorros");

    private final String tipo;

    TipoCuentaEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

 
}
