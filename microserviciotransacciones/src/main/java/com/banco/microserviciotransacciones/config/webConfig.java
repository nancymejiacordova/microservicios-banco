/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.config;

import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author nancy
 */
public class webConfig implements WebMvcConfigurer{
      @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter(new ClienteConverTemplate());
    }
}
