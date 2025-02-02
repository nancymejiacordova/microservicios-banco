/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

/**
 *
 * @author nancy
 */
@Component
public class JwtUtil {
    private static final String SECRET_KEY = "EsteEsUnSecretoMuyLargoParaJWTQueDebeSerSeguroConAlMenos32Caracteres";

    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public static String extractUsername(String token) {
        Claims claims = Jwts.parser() // Usamos parserBuilder() en lugar de parser()
                .setSigningKey(key) // Configuramos la clave secreta
                .build()
                .parseClaimsJws(token) // Parseamos el JWT
                .getBody(); // Extraemos las claims del cuerpo del JWT

        return claims.getSubject(); // Extraemos el nombre de usuario
    }
     
      public static List<String> extractRoles(String token) {
        Claims claims = Jwts.parser() // Usamos parserBuilder() en lugar de parser()
                .setSigningKey(key) // Configuramos la clave secreta
                .build()
                .parseClaimsJws(token) // Parseamos el JWT
                .getBody(); // Extraemos las claims del cuerpo del JWT

        return claims.get("roles", List.class); // Extraemos los roles del claim
    }

      public boolean validateToken(String token, String username) {
        try {
            return extractUsername(token).equals(username);
        } catch (Exception e) {
            return false;
        }
    }
    
    public static SecretKey getKey() {
        return key;
    }
}
