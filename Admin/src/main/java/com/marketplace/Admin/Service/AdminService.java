package com.marketplace.Admin.Service;

import com.marketplace.Admin.Model.AdminUser;
import com.marketplace.Admin.Repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
@Transactional
public class AdminService {

     @Autowired
    private AdminRepository adminRepository;

    // Obtener todos los administradores
    public List<AdminUser> getAllAdminUsers() {
        return adminRepository.findAll();
    }

    // Obtener un administrador por ID
    public Optional<AdminUser> getAdminUserById(Long id) {
        return adminRepository.findById(id);
    }

    // Crear un nuevo administrador
    public AdminUser createAdminUser(AdminUser adminUser) {
        // Puedes agregar lógica aquí para verificar si el correo ya está en uso
        return adminRepository.save(adminUser);
    }

    // Actualizar un administrador existente
    public AdminUser updateAdminUser(Long id, AdminUser adminUser) {
        if (adminRepository.existsById(id)) {
            adminUser.setIdAdmin(id);
            return adminRepository.save(adminUser);
        }
        return null;
    }

    // Eliminar un administrador por ID
    public void deleteAdminUser(Long id) {
        adminRepository.deleteById(id);
    }

    // Buscar un administrador por correo electrónico
    public Optional<AdminUser> getAdminUserByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

}
