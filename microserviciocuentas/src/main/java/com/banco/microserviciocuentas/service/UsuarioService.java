/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.service;

import com.banco.microserviciocuentas.model.Usuario;
import com.banco.microserviciocuentas.repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nancy
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
 
    // Método para obtener un usuario por su nombre de usuario (username)
    public Optional<Usuario> obtenerUsuario(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
}
