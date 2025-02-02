/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.model;

import com.banco.microserviciotransacciones.enums.TipoTransaccion;
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
@Table(name="transaccion")
public class Transaccion implements Serializable  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idtransaccion;
    
    @Column(nullable=false)
    private BigDecimal monto;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipotransaccion;
    
    @Column(nullable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechatransaccion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idcuentaorigen")
    private CuentaBancaria idcuentaorigen;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idcuentadestino")
    private CuentaBancaria idcuentadestino;
    
}
