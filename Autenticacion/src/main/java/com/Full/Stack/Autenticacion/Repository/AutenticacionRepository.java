package com.Full.Stack.Autenticacion.Repository;

import com.Full.Stack.Autenticacion.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface AutenticacionRepository extends JpaRepository<Usuario , Long> {

    Optional<Usuario> findByUsername(String username); // Encuentra paciente por el username

    Optional<Usuario> findByEmail(String email); // Encuentra paciente por el username

}
