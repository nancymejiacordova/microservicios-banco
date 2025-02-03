/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.template;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author nancy
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaTemp {

    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private BigDecimal monto;

}
