/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.serviceImpl;

import com.banco.microserviciotransacciones.enums.TipoTransaccion;
import com.banco.microserviciotransacciones.model.CuentaBancaria;
import com.banco.microserviciotransacciones.model.Transaccion;
import com.banco.microserviciotransacciones.repository.CuentaBancariaRepository;
import com.banco.microserviciotransacciones.repository.TransaccionRepository;
import com.banco.microserviciotransacciones.service.CuentaBancariaClientService;
import com.banco.microserviciotransacciones.service.CuentaBancariaService;
import com.banco.microserviciotransacciones.service.TransaccionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

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
    
     @Autowired
    private CuentaBancariaClientService cuentaBancariaClientService;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    private static final Logger logger = LoggerFactory.getLogger(CuentaBancariaServiceImpl.class);
    
    

    @Transactional
    @Override
    public Transaccion realizarTransaccion(Transaccion transaccion,CuentaBancaria cuenta,String token) {

        try {
            logger.info("Inicio de la transacción: {} para la cuenta: {}", transaccion.getTipotransaccion(), transaccion.getIdcuentadestino().getNumerocuenta());


            if (transaccion.getTipotransaccion() == TipoTransaccion.Retiros) {

                if (cuenta.getSaldoactual().compareTo(transaccion.getMonto()) < 0) {
                    logger.error("Error: saldo insuficiente para realizar el retiro de la cuenta: {}", transaccion.getIdcuentadestino().getNumerocuenta());
                    throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro");
                }

                cuenta.setSaldoactual(cuenta.getSaldoactual().subtract(transaccion.getMonto()));
                logger.info("Retiro realizado exitosamente de la cuenta: {}. Monto: {}", transaccion.getIdcuentadestino().getNumerocuenta(), transaccion.getMonto());
            } else if (transaccion.getTipotransaccion() == TipoTransaccion.Depositos) {

                cuenta.setSaldoactual(cuenta.getSaldoactual().add(transaccion.getMonto()));
                logger.info("Depósito realizado exitosamente en la cuenta: {}. Monto: {}", transaccion.getIdcuentadestino().getNumerocuenta(), transaccion.getMonto());
            }

            transaccion.setFechatransaccion(new Date());
            transaccion.setIdcuentadestino(cuenta);
            transaccionRepository.save(transaccion);
            System.out.println("guardo la table transaccion");
            cuentaBancariaClientService.actualizarCuenta(cuenta.getIdcuentabancaria(),cuenta.getSaldoactual(),token);

            logger.info("Transacción exitosa para la cuenta: {}", transaccion.getIdcuentadestino().getNumerocuenta());

            return transaccion;
        } catch (IllegalArgumentException e) {

            logger.error("Error al realizar la transacción para la cuenta: {}. Error: {}", transaccion.getIdcuentadestino().getNumerocuenta(), e.getMessage());
            throw e;
        } catch (Exception e) {

            logger.error("Error inesperado al realizar la transacción para la cuenta: {}. Error: {}", transaccion.getIdcuentadestino().getNumerocuenta(), e.getMessage(), e);
            throw new RuntimeException("Error inesperado al realizar la transacción", e);
        }

    }

    @Transactional
    @Override
    public List<Transaccion> obtenerHistorialTransaccionesPorCuenta(String numeroCuenta) {
        logger.info("Iniciando la consulta del historial de transacciones para la cuenta: {}", numeroCuenta);

        CuentaBancaria cuenta = cuentaBancariaService.obtenerCuentaPorNumero(numeroCuenta);
        if (cuenta == null) {
            logger.error("La cuenta bancaria con el número {} no existe.", numeroCuenta);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }

        List<Transaccion> transaccionesOrigen = transaccionRepository.findByIdcuentaorigen(cuenta);
        List<Transaccion> transaccionesDestino = transaccionRepository.findByIdcuentadestino(cuenta);

        List<Transaccion> historialTransacciones = new ArrayList<>();
        historialTransacciones.addAll(transaccionesOrigen);
        historialTransacciones.addAll(transaccionesDestino);

        if (historialTransacciones.isEmpty()) {
            logger.warn("No se encontraron transacciones para la cuenta {}", numeroCuenta);
        } else {
            logger.info("Se encontraron {} transacciones para la cuenta {}", historialTransacciones.size(), numeroCuenta);
        }

        return historialTransacciones;
    }

    @Transactional
    @Override
    public Transaccion realizarTransferencia( CuentaBancaria cuentaOrigen, CuentaBancaria cuentaDestino, BigDecimal monto,String token) {
        logger.info("Iniciando transferencia de la cuenta {} a la cuenta {}", cuentaOrigen.getNumerocuenta(), cuentaDestino.getSaldoactual());
       System.out.println("antes de debito:"+cuentaOrigen.getSaldoactual());
        

        // Validar que la cuenta de origen tiene suficiente saldo
        if (cuentaOrigen.getSaldoactual().compareTo(monto) < 0) {
            logger.warn("Saldo insuficiente en la cuenta de origen. Saldo actual: {}, Monto requerido: {}", cuentaOrigen.getSaldoactual(), monto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }

        // Realizar la transferencia: restar del saldo de la cuenta de origen y sumar en la cuenta destino
        System.out.println("antes de debito:"+cuentaOrigen.getSaldoactual());
        cuentaOrigen.setSaldoactual(cuentaOrigen.getSaldoactual().subtract(monto));
        System.out.println("despues de debito:"+cuentaOrigen.getSaldoactual());
        
        cuentaDestino.setSaldoactual(cuentaDestino.getSaldoactual().add(monto));

        // Crear la transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setIdcuentaorigen(cuentaOrigen);
        transaccion.setIdcuentadestino(cuentaDestino);
        transaccion.setMonto(monto);
        transaccion.setFechatransaccion(new Date());
        transaccion.setTipotransaccion(TipoTransaccion.Transferencia);

        // Guardar la transacción y las cuentas actualizadas
        transaccionRepository.save(transaccion);
        
        cuentaBancariaClientService.actualizarCuenta(cuentaOrigen.getIdcuentabancaria(),cuentaOrigen.getSaldoactual(),token);
        cuentaBancariaClientService.actualizarCuenta(cuentaDestino.getIdcuentabancaria(),cuentaDestino.getSaldoactual(),token);
//        cuentaBancariaRepository.save(cuentaOrigen);
//        cuentaBancariaRepository.save(cuentaDestino);

        logger.info("Transferencia realizada con éxito de la cuenta {} a la cuenta {}", cuentaOrigen.getNumerocuenta(), cuentaDestino.getNumerocuenta());
        return transaccion;
    }

}
