/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.model;

import com.banco.microserviciocuentas.enums.TipoCuentaEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author nancy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cuentaBancaria")
public class CuentaBancaria implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idcuentabancaria;
    
    @Column(unique=true,length=10,nullable=false)
    private String numerocuenta;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuentaEnum tipocuenta;
    
    @Column(nullable=false)
    private BigDecimal saldoactual;
    
    @Column(nullable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaultimaactualizacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idcliente")
    private Cliente idcliente;

      public CuentaBancaria(String numerocuenta, TipoCuentaEnum tipocuenta, BigDecimal saldo) {
        this.numerocuenta = numerocuenta;
        this.tipocuenta = tipocuenta;
        this.saldoactual = saldo;
    }
   
}
