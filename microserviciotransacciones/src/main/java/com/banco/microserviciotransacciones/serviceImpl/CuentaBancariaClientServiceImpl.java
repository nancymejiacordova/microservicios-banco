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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
    //  private final RestTemplate restTemplate;

    @Autowired
    public CuentaBancariaClientServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

//      @Autowired
//    public CuentaBancariaClientServiceImpl(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
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
    @Override
    public boolean verificarSaldoSuficiente(String numeroCuenta, BigDecimal monto, String token) {
        CuentaBancaria cuenta = obtenerCuenta(numeroCuenta, token);
        return cuenta.getSaldoactual().compareTo(monto) >= 0;
    }

    @Override
    public void actualizarCuenta(Long cuentaId, BigDecimal monto, String token) {
        System.out.println("inicio del metodo actualizar");

        // Crear el objeto CuentaBancaria con los datos necesarios
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setIdcuentabancaria(cuentaId);
        cuentaBancaria.setSaldoactual(monto);

        // Construir la URI y los encabezados
        String url = "http://localhost:8081/cuentaBancaria/actualizar/" + cuentaId; // Ajusta la URL
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<CuentaBancaria> requestEntity = new HttpEntity<>(cuentaBancaria, headers);

        // Realizar la solicitud sincrónica con WebClient usando block()
        try {
            // Hacer la llamada PUT y bloquear para esperar la respuesta
            webClient.put()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(cuentaBancaria) // Pasando el DTO con los datos
                    .retrieve()
                    .toBodilessEntity() // Obtener la respuesta sin cuerpo (Void)
                    .block();  // Bloquea hasta que se complete la solicitud

            // Si llega hasta aquí, la actualización fue exitosa
            System.out.println("Cuenta actualizada con éxito.");
        } catch (WebClientResponseException ex) {
            // Manejo de errores, si la llamada falla
            System.err.println("Error al actualizar la cuenta: " + ex.getMessage());
        } catch (Exception ex) {
            // Manejo de errores generales
            System.err.println("Error al hacer la solicitud: " + ex.getMessage());
        }
    }
}
