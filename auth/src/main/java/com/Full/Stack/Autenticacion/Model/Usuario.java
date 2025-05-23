package com.Full.Stack.Autenticacion.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) //Nombre de usuario (único para iniciar sesión)
    private String username;

    @Column(nullable = false) //Nombre real o nombre completo del usuario
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false )
    private String password;

    private String role;



}
