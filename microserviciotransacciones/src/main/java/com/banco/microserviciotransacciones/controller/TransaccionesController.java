/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.controller;

import com.banco.microserviciotransacciones.model.CuentaBancaria;
import com.banco.microserviciotransacciones.model.Transaccion;
import com.banco.microserviciotransacciones.service.TransaccionService;
import com.banco.microserviciotransacciones.serviceImpl.CuentaBancariaClientServiceImpl;
import com.banco.microserviciotransacciones.serviceImpl.CuentaBancariaServiceImpl;
import com.banco.microserviciotransacciones.template.TransferenciaTemp;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author nancy
 */
@RestController
@RequestMapping("/transacciones")
public class TransaccionesController {

    @Autowired
    private TransaccionService transaccionService;
    private static final Logger logger = LoggerFactory.getLogger(CuentaBancariaServiceImpl.class);

    private final CuentaBancariaClientServiceImpl cuentaBancariaClientServiceImpl;

    @Autowired
    public TransaccionesController(CuentaBancariaClientServiceImpl cuentaBancariaClientServiceImpl) {
        this.cuentaBancariaClientServiceImpl = cuentaBancariaClientServiceImpl;
    }

    @GetMapping("/historial/prueba/{numeroCuenta}")
    public CuentaBancaria obtenerHistorialTransacciones(@PathVariable String numeroCuenta, @RequestHeader("Authorization") String authorizationHeader) {
        // Llamar al servicio para obtener la cuenta
        System.out.println("controlador de obtencion de cuenta micro 1 ");

        return cuentaBancariaClientServiceImpl.obtenerCuenta(numeroCuenta, authorizationHeader.substring(7));
    }

    @PostMapping("/transaccion")
    ResponseEntity<Object> realizarTransaccion(@RequestBody Transaccion transaccion, @RequestHeader("Authorization") String authorizationHeader) {

        Map<String, String> response = new HashMap<>();
        boolean estadosaldo = true;
        CuentaBancaria cuenta = cuentaBancariaClientServiceImpl.obtenerCuenta(transaccion.getIdcuentadestino().getNumerocuenta(), authorizationHeader.substring(7));
        if (cuenta != null) {
            try {
                
                if("Retiros".equals(transaccion.getTipotransaccion())){
                estadosaldo=cuentaBancariaClientServiceImpl.verificarSaldoSuficiente(transaccion.getIdcuentadestino().getNumerocuenta(), transaccion.getMonto(), authorizationHeader.substring(7));
                }else{
                    estadosaldo = true;
                }   
                    
                 if(estadosaldo){                
                    Transaccion nuevaTransaccion = transaccionService.realizarTransaccion(transaccion, cuenta, authorizationHeader.substring(7));
                    logger.info("Operación de depósito exitosa. Transacción realizada para la cuenta: {} con monto: {}",
                            transaccion.getIdcuentadestino().getNumerocuenta(), transaccion.getMonto());
                    return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTransaccion);
                } else {
                    response.put("error", "Saldo insuficiente para realizar el retiro");
                    response.put("mensaje", "Error al realizar el depósito. Saldo insuficiente para la cuenta: {}. Error: {}");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            } catch (Exception e) {
                // Registro de log para cualquier error inesperado
                logger.error("Error inesperado al realizar el depósito para la cuenta: {}. Error: {}",
                        transaccion.getIdcuentadestino().getNumerocuenta(), e.getMessage(), e);

                // Respuesta con código HTTP 500 (Internal Server Error) por error inesperado
                response.put("error", "Error inesperado al procesar la transacción");
                response.put("mensaje", e.getMessage());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            response.put("error", "Cuenta no encontrada");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    // Endpoint para consultar el historial de transacciones
    @GetMapping("/historial/{numeroCuenta}")
    public ResponseEntity<List<Transaccion>> obtenerHistorial(@PathVariable String numeroCuenta) {
        logger.info("Recibiendo solicitud para obtener el historial de transacciones de la cuenta {}", numeroCuenta);

        try {
            List<Transaccion> historial = transaccionService.obtenerHistorialTransaccionesPorCuenta(numeroCuenta);

            //Si no se encontraron transacciones, devolvemos un 404 con un mensaje adecuado
            if (historial.isEmpty()) {
                logger.warn("No se encontraron transacciones para la cuenta {}", numeroCuenta);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(historial);
            }

            logger.info("Se encontraron {} transacciones para la cuenta {}", historial.size(), numeroCuenta);
            return ResponseEntity.ok(historial);

        } catch (ResponseStatusException ex) {
            logger.error("Error al consultar el historial de transacciones para la cuenta {}: {}", numeroCuenta, ex.getMessage());
            return ResponseEntity.status(ex.getStatus()).body(null);
        } catch (Exception ex) {
            logger.error("Error inesperado al procesar la solicitud de historial para la cuenta {}: {}", numeroCuenta, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/transferir")
    public ResponseEntity<Transaccion> realizarTransferencia(@RequestBody TransferenciaTemp transferenciaTemp,@RequestHeader("Authorization") String authorizationHeader) {
        logger.info("Solicitud de transferencia recibida: origen={} destino={} monto={}",
                transferenciaTemp.getNumeroCuentaOrigen(),
                transferenciaTemp.getNumeroCuentaDestino(),
                transferenciaTemp.getMonto());

        try {
            
            CuentaBancaria cuentaOrigen = cuentaBancariaClientServiceImpl.obtenerCuenta(transferenciaTemp.getNumeroCuentaOrigen(), authorizationHeader.substring(7));
            CuentaBancaria cuentaDestino = cuentaBancariaClientServiceImpl.obtenerCuenta(transferenciaTemp.getNumeroCuentaDestino(), authorizationHeader.substring(7));
            
            if(cuentaOrigen!=null && cuentaDestino!=null){
            Transaccion transaccion = transaccionService.realizarTransferencia(
                    cuentaOrigen,
                    cuentaDestino,
                    transferenciaTemp.getMonto(),authorizationHeader.substring(7));

            logger.info("Transferencia exitosa de la cuenta {} a la cuenta {}",
                    transferenciaTemp.getNumeroCuentaOrigen(),
                    transferenciaTemp.getNumeroCuentaDestino());
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccion);
            }else{
                logger.info("Se encontraron {} transacciones para la cuenta {}", cuentaOrigen, cuentaOrigen);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (ResponseStatusException e) {
            logger.error("Error en la transferencia: {}", e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }
    
    
}
