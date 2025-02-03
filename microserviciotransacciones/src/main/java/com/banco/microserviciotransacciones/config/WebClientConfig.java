/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author nancy
 */

@Configuration
public class WebClientConfig {
    
     @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8081/cuentaBancaria/") // URL del microservicio de cuentas
                .build();
    }
    
}
