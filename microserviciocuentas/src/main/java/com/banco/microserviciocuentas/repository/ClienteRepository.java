/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.banco.microserviciocuentas.repository;


import com.banco.microserviciocuentas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nancy Mejia
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>{
    
}
