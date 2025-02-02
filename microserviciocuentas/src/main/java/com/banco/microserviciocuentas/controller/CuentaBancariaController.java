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
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    private static final Logger logger = LoggerFactory.getLogger(CuentaBancariaController.class);


    @GetMapping()
    public Page<CuentaBancaria> listar(Pageable pageable){
        var listCuentas = this.cuentaBancariaService.listCuentas(pageable);
        if (!listCuentas.hasContent()) {
            throw new Exceptions(ErrorEnum.NO_CONTENT, HttpStatus.NO_CONTENT.value());
        }
        return listCuentas;
    }

    
 @GetMapping("/{numeroCuenta}")
    public CuentaBancaria obtenerCuentaPorNumero(@PathVariable String numeroCuenta) {
         logger.info("Recibiendo solicitud para consultar la cuenta con número: {}", numeroCuenta);
        
        try {
            CuentaBancaria cuenta = cuentaBancariaService.obtenerCuentaPorNumero(numeroCuenta);
            logger.info("Cuenta con número {} encontrada exitosamente", numeroCuenta);
            return cuenta;
        } catch (Exception e) {
            logger.error("Error al consultar la cuenta con número {}: {}", numeroCuenta, e.getMessage());
            throw e;  
        }
    }

    

    /**
     *
     * @param cuentaCreateTemp
     * @return
     */

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaBancaria crear(@RequestBody CuentaCreateTemp cuentaCreateTemp, BindingResult result) {
      logger.info("Recibiendo solicitud para crear una cuenta bancaria con datos: {}", cuentaCreateTemp);

        if (result.hasErrors()) {
            logger.warn("Errores de validación al intentar crear la cuenta: ");
            List<FieldError> errores = result.getFieldErrors();
            for (FieldError error : errores) {
                logger.warn("Campo: {} - Error: {}", error.getField(), error.getDefaultMessage());
            }
            throw new Exceptions(ErrorEnum.DATOS_ENTRADA_INVALIDOS, HttpStatus.BAD_REQUEST.value());
        }

        try {
            CuentaBancaria cuenta = this.cuentaBancariaService.crearCuentaBancaria(cuentaCreateTemp);
            logger.info("Cuenta bancaria creada exitosamente con número: {}", cuenta.getNumerocuenta());
            return cuenta;
        } catch (Exception e) {
            logger.error("Error al crear la cuenta bancaria: {}", e.getMessage());
            throw e;
        }
    }

    
  // Endpoint para obtener cuentas por idcliente
    @GetMapping("/cuentas/{idcliente}")
    public List<CuentaBancaria> getCuentasPorCliente(@PathVariable Long idcliente) {
        logger.info("Solicitud recibida para obtener cuentas bancarias del cliente con id: {}", idcliente);
        List<CuentaBancaria> cuentas = cuentaBancariaService.obtenerCuentasPorCliente(idcliente);
        
        if (cuentas.isEmpty()) {
            logger.warn("No se encontraron cuentas bancarias para el cliente con id: {}", idcliente);
        } else {
            logger.info("Se encontraron {} cuentas bancarias para el cliente con id: {}", cuentas.size(), idcliente);
        }

        return cuentas;
    }

}
