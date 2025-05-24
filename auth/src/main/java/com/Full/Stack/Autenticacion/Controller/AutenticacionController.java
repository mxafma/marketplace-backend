package com.Full.Stack.Autenticacion.Controller;

import com.Full.Stack.Autenticacion.Model.Usuario;
import com.Full.Stack.Autenticacion.Service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    private AutenticacionService AutenticacionService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //Endpoint para registrar usuario
    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        AutenticacionService.registrarUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado con exito");
    }

    //Endpoint para login (validacion Usuario jwt)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        //Se busca primero el usuario por username
        Optional<Usuario> usuarioExistente = AutenticacionService.obtenerUsuarioPorUsername(usuario.getUsername()); 

         if (usuarioExistente.isPresent()) {
            // Validar la contraseña
            if (passwordEncoder.matches(usuario.getPassword(), usuarioExistente.get().getPassword())) {
                // Si la contraseña es correcta, generamos el token JWT (paso siguiente)
                String jwtToken = generarToken(usuario);
                return ResponseEntity.ok(jwtToken);
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

        // Método para generar el JWT (aún por implementar)
    private String generarToken(Usuario usuario) {
        // Implementaremos esto en el siguiente paso
        return "token_dummy";  // Este es solo un marcador de posición
    }

        
}


