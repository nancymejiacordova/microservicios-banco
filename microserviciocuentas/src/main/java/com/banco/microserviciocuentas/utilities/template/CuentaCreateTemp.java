/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.utilities.template;


import com.banco.microserviciocuentas.enums.TipoCuentaEnum;
import com.banco.microserviciocuentas.template.ClienteTemp;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Nancy Mejia
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaCreateTemp {
    private String numerocuenta;
    private TipoCuentaEnum tipocuenta;
    private BigDecimal saldoInicial;
    private ClienteTemp cliente;

}
