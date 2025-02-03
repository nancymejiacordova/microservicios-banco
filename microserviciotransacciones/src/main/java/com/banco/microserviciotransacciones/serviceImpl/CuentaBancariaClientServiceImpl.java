/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.banco.microserviciotransacciones.serviceImpl;

import com.banco.microserviciotransacciones.model.CuentaBancaria;
import com.banco.microserviciotransacciones.service.CuentaBancariaClientService;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 *
 * @author nancy
 */

@Service
public class CuentaBancariaClientServiceImpl implements CuentaBancariaClientService {

private static final Logger logger = LoggerFactory.getLogger(CuentaBancariaClientServiceImpl.class);

    // Inyección de WebClient
    private final WebClient webClient;

    @Autowired
    public CuentaBancariaClientServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    // Método para obtener los datos de una cuenta bancaria
@Override
    public CuentaBancaria obtenerCuenta(String numeroCuenta, String token) {
        
        // Consumimos el microservicio de cuentas con el número de cuenta
        return webClient.get()
                .uri("/{numeroCuenta}", numeroCuenta) // Suponiendo que tienes un endpoint de tipo GET /cuentas/{numeroCuenta}
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token) 
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    logger.error("Error al obtener la cuenta bancaria con el número {}", numeroCuenta);
                    return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    logger.error("Error interno al consultar la cuenta bancaria");
                    return Mono.error(new RuntimeException("Error del servidor en el microservicio de cuentas"));
                })
                .bodyToMono(CuentaBancaria.class) // Devolvemos la entidad CuentaBancaria
                .block(); // Usamos block() para hacerlo sincrónico (bloqueante), si prefieres hacerlo asincrónico usa Mono o Flux.
    }

    // Verificamos si la cuenta tiene saldo suficiente
//    public boolean verificarSaldoSuficiente(String numeroCuenta, BigDecimal monto) {
//        CuentaBancaria cuenta = obtenerCuenta(numeroCuenta);
//        return cuenta.getSaldoactual().compareTo(monto) >= 0;
//    }
}