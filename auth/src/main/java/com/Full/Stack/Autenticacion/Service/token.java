package com.Full.Stack.Autenticacion.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import com.Full.Stack.Autenticacion.Model.Usuario;

public class token {

     private String secretKey = "mi_clave_secreta";  // En producción usa un secreto más seguro

    // Método para generar el JWT
    public String generarToken(Usuario usuario) {
        return Jwts.builder()
            .setSubject(usuario.getUsername())  // Establecemos el nombre de usuario como subject
            .setIssuedAt(new Date())  // Fecha de emisión del token
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Expira en 1 hora
            .signWith(SignatureAlgorithm.HS256, secretKey)  // Firma con el algoritmo HS256 y la clave secreta
            .compact();
    }

}