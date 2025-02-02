/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.service;

import com.banco.microserviciocuentas.model.CuentaBancaria;
import com.banco.microserviciocuentas.utilities.template.ClienteCreateTemp;
import com.banco.microserviciocuentas.utilities.template.CuentaCreateTemp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author nancy
 */
public interface CuentaBancariaService {
    
     CuentaBancaria crearCuentaBancaria(CuentaCreateTemp cuentaCreateTemp);
     CuentaBancaria obtenerCuentaPorNumero(String numeroCuenta);
     List<CuentaBancaria> obtenerCuentasPorCliente(Long idcliente);
     Page<CuentaBancaria> listCuentas(Pageable pageable); 
    
    
}
