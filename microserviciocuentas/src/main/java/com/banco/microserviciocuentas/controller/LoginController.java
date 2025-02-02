/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.controller;

import com.banco.microserviciocuentas.config.JwtUtil;
import com.banco.microserviciocuentas.model.Usuario;
import com.banco.microserviciocuentas.service.UsuarioService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/auth")
public class LoginController {
    private final JwtUtil jwtUtil;

    public LoginController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Autowired
    private UsuarioService usuarioService;

     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario user) {
        String token;
        Optional<Usuario> usuario = usuarioService.obtenerUsuario(user.getNombre());
        if (usuario.isPresent()) {
            if(usuario.get().getPassword().equals(user.getPassword())){
                token = JwtUtil.generateToken(usuario.get());
            }
            else{
                return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
            }
        } 
        else{
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }        
        return ResponseEntity.ok(token);
    }
}
