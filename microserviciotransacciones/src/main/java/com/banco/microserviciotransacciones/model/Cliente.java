/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.banco.microserviciotransacciones.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cliente")
public class Cliente  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcliente;
    
    @Column(unique=true,length=255,nullable=false)
    private String correoelectronico;
    
    
    @Column(unique=true,length=255,nullable=false)
    private String direccion;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    
    @Column(unique=true,length=255,nullable=false)
    private String nombre;
    
    @Column(unique=true,length=255,nullable=false)
    private String telefono;
  
}