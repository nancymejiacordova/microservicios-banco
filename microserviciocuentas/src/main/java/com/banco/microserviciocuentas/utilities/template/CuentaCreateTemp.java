/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.utilities.template;


import com.banco.microserviciocuentas.enums.TipoCuentaEnum;
import com.banco.microserviciocuentas.template.ClienteTemp;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    
    @NotEmpty(message = "El número de cuenta no puede estar vacío")
    @Size(min = 10, max = 20, message = "El número de cuenta debe tener entre 10 y 20 caracteres")
    private String numerocuenta;
    
    private TipoCuentaEnum tipocuenta;
    
    @NotNull(message = "El saldo inicial no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El saldo inicial debe ser mayor que cero")
    private BigDecimal saldoInicial;
    
    @NotEmpty(message = "El tipo de cuenta no puede estar vacío")
    @Pattern(regexp = "^(Corriente|Ahorros)$", message = "El tipo de cuenta debe ser 'Corriente' o 'Ahorro'")
    private ClienteTemp cliente;

}
