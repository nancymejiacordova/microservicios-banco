/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.controller;

import com.banco.microserviciotransacciones.model.Transaccion;
import com.banco.microserviciotransacciones.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nancy
 */
@RestController
@RequestMapping("/transacciones")
public class TransaccionesController {

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping("/deposito")
    public ResponseEntity<Transaccion> realizarDeposito(@RequestBody Transaccion transaccion) {
        Transaccion nuevaTransaccion = transaccionService.realizarDeposito(transaccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTransaccion);
    }

//    @PostMapping("/retiro")
//    public ResponseEntity<Transaccion> realizarRetiro(@RequestBody Transaccion transaccion) {
//        Transaccion nuevaTransaccion = transaccionService.realizarRetiro(transaccion);
//        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTransaccion);
//    }
//
//    @PostMapping("/transferencia")
//    public ResponseEntity<Transaccion> realizarTransferencia(@RequestBody Transaccion transaccion) {
//        Transaccion nuevaTransaccion = transaccionService.realizarTransferencia(transaccion);
//        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTransaccion);
//    }
//
//    @GetMapping("/{numeroCuenta}")
//    public ResponseEntity<List<Transaccion>> obtenerHistorial(@PathVariable String numeroCuenta) {
//        List<Transaccion> transacciones = transaccionService.obtenerHistorial(numeroCuenta);
//        return ResponseEntity.ok(transacciones);
//    }
}
