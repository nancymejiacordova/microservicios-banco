/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.service;
import com.banco.microserviciocuentas.model.Cliente;

/**
 *
 * @author Nancy Mejia
 */
public interface ClienteService {
    
        //Cliente buscarCliente(Long clienteId);
        Cliente findByidCliente(Long idCliente);

    
}
