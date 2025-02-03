/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.repository;

import com.banco.microserviciotransacciones.model.CuentaBancaria;
import com.banco.microserviciotransacciones.model.Transaccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nancy
 */
@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByIdcuentaorigen(Long cuentaOrigenId);
    List<Transaccion> findByIdcuentaorigen(CuentaBancaria cuentaBancaria);
    List<Transaccion> findByIdcuentadestino(CuentaBancaria cuentaBancaria);
}
