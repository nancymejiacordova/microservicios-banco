/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.serviceImpl;

import com.banco.microserviciocuentas.exceptions.ErrorEnum;
import com.banco.microserviciocuentas.exceptions.Exceptions;
import com.banco.microserviciocuentas.model.CuentaBancaria;
import com.banco.microserviciocuentas.repository.CuentaBancariaRepository;
import com.banco.microserviciocuentas.service.ClienteService;
import com.banco.microserviciocuentas.service.CuentaBancariaService;
import com.banco.microserviciocuentas.utilities.template.CuentaCreateTemp;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nancy
 */
@Service
@RequiredArgsConstructor
public class CuentaBancariaServiceImpl implements CuentaBancariaService {
    
    private final CuentaBancariaRepository cuentaBancariaRepository;
    private final ConversionService conversionService;
    private final ClienteService clienteService;

    
    @Override
    @Transactional()
    public CuentaBancaria crearCuentaBancaria(CuentaCreateTemp cuentaCreateTemp) {

        if (cuentaCreateTemp.getSaldoInicial().compareTo(BigDecimal.ZERO) < 0) {
            throw new Exceptions(ErrorEnum.CREAR_CUENTA_SALDO_INVALIDO, HttpStatus.BAD_REQUEST.value());
        }

        var cliente = clienteService.buscarCliente(cuentaCreateTemp.getCliente().getIdCliente());
        var cuentaEntity = CuentaBancaria.builder()
            .cliente(cliente)
            .numerocuenta(cuentaCreateTemp.getNumerocuenta())
            .tipocuenta(cuentaCreateTemp.getTipocuenta())
            .saldoactual(cuentaCreateTemp.getSaldoInicial())
            .build();
        return this.cuentaBancariaRepository.save(cuentaEntity);
       // return this.conversionService.convert(cuentaEntity, CuentaSecTemp.class);
    }

    @Override
    public Page<CuentaBancaria> listCuentas(Pageable pageable) {
        return this.cuentaBancariaRepository.findAll(pageable);
    }

    @Override
    public CuentaBancaria findByidCliente(Long idCliente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    /**
     * {@inheritDoc}
     */
    
    
}
