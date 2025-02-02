/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.utilities.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author Nancy Mejia
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateTemp {
    private String nombre;
    private String correoElectronico;
    private String telefono;
    private String direccion;
    private String fechaCreacion;
  
}
