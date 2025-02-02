/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.serviceImpl;

import com.banco.microserviciotransacciones.enums.TipoTransaccion;
import com.banco.microserviciotransacciones.model.CuentaBancaria;
import com.banco.microserviciotransacciones.model.Transaccion;
import com.banco.microserviciotransacciones.repository.TransaccionRepository;
import com.banco.microserviciotransacciones.service.CuentaBancariaService;
import com.banco.microserviciotransacciones.service.TransaccionService;
import java.time.LocalDateTime;
import java.util.Date;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nancy
 */
@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {
    
    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @Transactional
    @Override
    public Transaccion realizarDeposito(Transaccion transaccion) {
        CuentaBancaria cuenta = cuentaBancariaService.obtenerCuentaPorNumero(transaccion.getIdcuentaorigen().getNumerocuenta());
        cuenta.setSaldoactual(cuenta.getSaldoactual().add(transaccion.getMonto()));
        transaccion.setTipotransaccion(TipoTransaccion.Depositos);
        transaccion.setFechatransaccion(new Date());
        transaccionRepository.save(transaccion);
        return transaccion;
    }
//
//    @Transactional
//    public Transaccion realizarRetiro(Transaccion transaccion) {
//        CuentaBancaria cuenta = cuentaBancariaService.obtenerCuentaPorNumero(transaccion.getIdcuentaorigen().getNumerocuenta());
//        if (cuenta.getSaldoactual().compareTo(transaccion.getMonto()) < 0) {
//            throw new InsufficientFundsException("Saldo insuficiente");
//        }
//        cuenta.setSaldo(cuenta.getSaldo().subtract(transaccion.getMonto()));
//        transaccion.setTipoTransaccion("retiro");
//        transaccion.setFecha(LocalDateTime.now());
//        transaccionRepository.save(transaccion);
//        return transaccion;
//    }
//
//    @Transactional
//    public Transaccion realizarTransferencia(Transaccion transaccion) {
//        // Validar cuentas y saldo, luego transferir
//        CuentaBancaria cuentaOrigen = cuentaBancariaService.obtenerCuentaPorNumero(transaccion.getCuentaOrigen().getNumeroCuenta());
//        CuentaBancaria cuentaDestino = cuentaBancariaService.obtenerCuentaPorNumero(transaccion.getCuentaDestino().getNumeroCuenta());
//        if (cuentaOrigen.getSaldo().compareTo(transaccion.getMonto()) < 0) {
//            throw new InsufficientFundsException("Saldo insuficiente para transferencia");
//        }
//        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(transaccion.getMonto()));
//        cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(transaccion.getMonto()));
//        transaccion.setTipoTransaccion("transferencia");
//        transaccion.setFecha(LocalDateTime.now());
//        transaccionRepository.save(transaccion);
//        return transaccion;
//    }
}
