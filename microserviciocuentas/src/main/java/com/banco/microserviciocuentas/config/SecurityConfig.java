/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author nancy
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // Permite todas las solicitudes sin autenticaci�n
//        http
//            .authorizeRequests()
//                .antMatchers("/**").permitAll() // Permite todas las rutas
//            .anyRequest().permitAll() // Tambi�n permite cualquier otra solicitud
//            .and()
//            .csrf().disable(); // Desactiva CSRF (solo si es necesario, y con precauci�n)
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/auth/login").permitAll()  
            .antMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()     
            .antMatchers("/cuentaBancaria/consulta/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/cuentaBancaria/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}