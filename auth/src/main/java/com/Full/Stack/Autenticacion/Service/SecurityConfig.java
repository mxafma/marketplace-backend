package com.Full.Stack.Autenticacion.Service;

    
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration // Indica que esta clase es una fuente de definiciones de beans.
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring Security.
public class SecurityConfig {

    @Bean // Declara un bean gestionado por Spring, en este caso, la cadena de filtros de seguridad.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilita la protección CSRF (Cross-Site Request Forgery). Es común deshabilitarla para APIs REST
            // ya que se usa un enfoque de seguridad sin estado (como JWT).
            .csrf(AbstractHttpConfigurer::disable)
            // Configura las reglas de autorización para las solicitudes HTTP.
            .authorizeHttpRequests(authorize -> authorize
                // Permite que las solicitudes a /autenticacion/registro y /autenticacion/login
                // sean accedidas por cualquiera (sin autenticación).
                .requestMatchers("/autenticacion/registro", "/autenticacion/login").permitAll()
                // Cualquier otra solicitud (anyRequest()) requiere autenticación (authenticated()).
                .anyRequest().authenticated()
            );
        return http.build(); // Construye y devuelve la cadena de filtros de seguridad.
    }
}