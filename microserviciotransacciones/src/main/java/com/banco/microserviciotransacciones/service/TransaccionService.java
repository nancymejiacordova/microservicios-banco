/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.service;

import com.banco.microserviciotransacciones.model.CuentaBancaria;
import com.banco.microserviciotransacciones.model.Transaccion;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author nancy
 */
        
public interface TransaccionService {
    
   Transaccion realizarTransaccion(Transaccion transaccion, CuentaBancaria cuenta);
   List<Transaccion> obtenerHistorialTransaccionesPorCuenta(String numeroCuenta);
   Transaccion realizarTransferencia(String numeroCuentaOrigen, String numeroCuentaDestino, BigDecimal monto);
}
