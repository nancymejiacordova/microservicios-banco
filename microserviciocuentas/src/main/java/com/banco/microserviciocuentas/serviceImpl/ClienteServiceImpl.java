/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.serviceImpl;

import com.banco.microserviciocuentas.exceptions.ErrorEnum;
import com.banco.microserviciocuentas.exceptions.Exceptions;
import com.banco.microserviciocuentas.model.Cliente;
import com.banco.microserviciocuentas.repository.ClienteRepository;
import com.banco.microserviciocuentas.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nancy Mejia
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService{

    private final ClienteRepository clienteRepository;
    @Override
    @Transactional(readOnly = true)
    public Cliente findByidCliente(Long clienteId) {
       return clienteRepository.findById(clienteId)
            .orElseThrow(() -> new Exceptions(ErrorEnum.CREAR_CUENTA_CLIENTE_NOT_FOUND));
    }

}
