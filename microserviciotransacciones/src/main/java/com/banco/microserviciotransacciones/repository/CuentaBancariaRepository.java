/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.repository;

import com.banco.microserviciotransacciones.model.CuentaBancaria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nancy
 */
@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long>{
    
    // Optional<CuentaBancaria> findByNumerocuenta(String numerocuenta);
   CuentaBancaria findByNumerocuenta(String numerocuenta);
     
    
}
