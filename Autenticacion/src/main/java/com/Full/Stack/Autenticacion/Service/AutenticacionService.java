package com.Full.Stack.Autenticacion.Service;

import com.Full.Stack.Autenticacion.Model.Usuario;
import com.Full.Stack.Autenticacion.Repository.AutenticacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class AutenticacionService {

    @Autowired
    private AutenticacionRepository autenticacionRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //REGISTRAR NUEVO USUARIO
    public Usuario registrarUsuario(Usuario usuario) {
        String contraseñacifrada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(contraseñacifrada);
        return autenticacionRepository.save(usuario);
    }

    public Optional<Usuario> obtenerUsuarioPorUsername(String username) {
    return autenticacionRepository.findByUsername(username);
    }

    


}
