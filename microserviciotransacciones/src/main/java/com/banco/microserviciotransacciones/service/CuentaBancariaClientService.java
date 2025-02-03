/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.banco.microserviciotransacciones.service;

import com.banco.microserviciotransacciones.model.CuentaBancaria;
import java.math.BigDecimal;
import reactor.core.publisher.Mono;

/**
 *
 * @author nancy
 */
public interface CuentaBancariaClientService {
    
     CuentaBancaria obtenerCuenta(String numeroCuenta, String token);
     boolean verificarSaldoSuficiente(String numeroCuenta, BigDecimal monto, String token);
     void actualizarCuenta(Long cuentaId, BigDecimal monto, String token);
    
}
