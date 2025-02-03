/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.enums;

/**
 *
 * @author nancy
 */
public enum TipoTransaccion {
    
    Depositos("Depositos"),
    Retiros("Retiros"),
    Transferencia("Transferencia");
 

    private final String tipo;

    TipoTransaccion(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

 
    
}
