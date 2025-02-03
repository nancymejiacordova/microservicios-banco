/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.banco.microserviciotransacciones.service;

import com.banco.microserviciotransacciones.model.CuentaBancaria;

/**
 *
 * @author nancy
 */
public interface CuentaBancariaClientService {
    
     CuentaBancaria obtenerCuenta(String numeroCuenta, String token);
    
}
