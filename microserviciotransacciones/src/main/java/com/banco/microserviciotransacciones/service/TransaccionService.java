/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.service;

import com.banco.microserviciotransacciones.model.Transaccion;
import org.springframework.stereotype.Service;

/**
 *
 * @author nancy
 */
        
public interface TransaccionService {
    
   Transaccion realizarDeposito(Transaccion transaccion);
    
}
