/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.controller;

import com.banco.microserviciocuentas.exceptions.ErrorEnum;
import com.banco.microserviciocuentas.exceptions.Exceptions;
import com.banco.microserviciocuentas.model.CuentaBancaria;
import com.banco.microserviciocuentas.service.CuentaBancariaService;
import com.banco.microserviciocuentas.utilities.template.CuentaCreateTemp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nancy Mejia
 */
@RestController
@RequestMapping(path = "/cuentaBancaria", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CuentaBancariaController {
   
    private final CuentaBancariaService cuentaBancariaService;


    @GetMapping()
    public Page<CuentaBancaria> listar(Pageable pageable){
        var listCuentas = this.cuentaBancariaService.listCuentas(pageable);
        if (!listCuentas.hasContent()) {
            throw new Exceptions(ErrorEnum.NO_CONTENT, HttpStatus.NO_CONTENT.value());
        }
        return listCuentas;
    }

    
    @GetMapping(value = "/detalle/{numeroCuenta}")
    public CuentaBancaria buscarPorIdCliente(@PathVariable Long idCliente) {
        return this.cuentaBancariaService.findByidCliente(idCliente);
    }

    

    /**
     *
     * @param cuentaCreateTemp
     * @return
     */

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaBancaria crear(@RequestBody CuentaCreateTemp cuentaCreateTemp) {
        return this.cuentaBancariaService.crearCuentaBancaria(cuentaCreateTemp);
    }

    
  

}
