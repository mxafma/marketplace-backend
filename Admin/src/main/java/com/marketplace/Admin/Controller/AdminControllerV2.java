package com.marketplace.Admin.Controller;


import com.marketplace.Admin.Model.AdminUser;
import com.marketplace.Admin.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin-usersV2")
public class AdminControllerV2 {

    @Autowired
    private AdminService adminService;

    // Obtener todos los administradores
    @GetMapping
    public List<AdminUser> getAllAdminUsers() {
        return adminService.getAllAdminUsers();
    }

    // Obtener un administrador por ID
    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminUserById(@PathVariable Long id) {
        Optional<AdminUser> adminUser = adminService.getAdminUserById(id);
        return adminUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo administrador
    @PostMapping
    public ResponseEntity<AdminUser> createAdminUser(@RequestBody AdminUser adminUser) {
        // Llamada al servicio para crear el administrador
        AdminUser createdAdminUser = adminService.createAdminUser(adminUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdminUser);
    }

    // Actualizar un administrador
    @PutMapping("/{id}")
    public ResponseEntity<AdminUser> updateAdminUser(@PathVariable Long id, @RequestBody AdminUser adminUser) {
        AdminUser updatedAdminUser = adminService.updateAdminUser(id, adminUser);
        return updatedAdminUser != null
                ? ResponseEntity.ok(updatedAdminUser)
                : ResponseEntity.notFound().build();
    }

    // Eliminar un administrador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long id) {
        adminService.deleteAdminUser(id);
        return ResponseEntity.noContent().build(); // Responde con 204 No Content
    }

    // Buscar un administrador por email
    @GetMapping("/email/{email}")
    public ResponseEntity<AdminUser> getAdminUserByEmail(@PathVariable String email) {
        Optional<AdminUser> adminUser = adminService.getAdminUserByEmail(email);
        return adminUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
