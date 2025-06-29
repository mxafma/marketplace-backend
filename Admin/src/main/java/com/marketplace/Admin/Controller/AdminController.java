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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin-users")
@Tag(name = "Administradores", description = "Operaciones Crear,Editar,Elminar y obtener de administradores")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Obtener todos los administradores
    @GetMapping
    @Operation(summary ="Obtener todos los administradores", description = "Devuelve una lista de todos los administradores registrados")
    @ApiResponse(responseCode = "200", description = "Listado obtenido con exito",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AdminUser.class)))
    public List<AdminUser> getAllAdminUsers() {
        return adminService.getAllAdminUsers();
    }

    // Obtener un administrador por ID
    @GetMapping("/{id}")
    @Operation(summary ="Obtener administradores por id", description = "Devuelve una lista de un administrador segun el id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "admin encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AdminUser.class))),
        @ApiResponse(responseCode = "400", description = "admin no encontrado")
    })
    public ResponseEntity<AdminUser> getAdminUserById(@PathVariable Long id) {
        Optional<AdminUser> adminUser = adminService.getAdminUserById(id);
        return adminUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo administrador
    @PostMapping
    @Operation(summary ="Crea Nuevo administrador", description = "Crea un nuevo administrador en el sistema")
    @ApiResponse(responseCode = "200", description = "Administrador Creado exitosamente")
    public ResponseEntity<AdminUser> createAdminUser(@RequestBody AdminUser adminUser) {
        AdminUser createdAdminUser = adminService.createAdminUser(adminUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdminUser);
    }

    // Actualizar un administrador
    @PutMapping("/{id}")
    @Operation(summary ="Actualiza un admin por su id", description = "Actualiza los datos de un administrador especificado por el id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "admin actualizado correctamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AdminUser.class))),
        @ApiResponse(responseCode = "400", description = "admin no encontrado")
    })
    public ResponseEntity<AdminUser> updateAdminUser(@PathVariable Long id, @RequestBody AdminUser adminUser) {
        AdminUser updatedAdminUser = adminService.updateAdminUser(id, adminUser);
        return updatedAdminUser != null
                ? ResponseEntity.ok(updatedAdminUser)
                : ResponseEntity.notFound().build();
    }

    // Eliminar un administrador
    @DeleteMapping("/{id}")
    @Operation(summary ="Elimina un admin por su id", description = "Eliminia un admin especificado por su id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "admin eliminado exitosamente"),
        @ApiResponse(responseCode = "400", description = "admin no encontrado")
    })
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long id) {
        adminService.deleteAdminUser(id);
        return ResponseEntity.noContent().build(); // Responde con 204 No Content
    }

    // Buscar un administrador por email
    @GetMapping("/email/{email}")
    @Operation(summary ="Obtener administradores por su email", description = "Devuelve una lista de un administrador segun el email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "admin encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AdminUser.class))),
        @ApiResponse(responseCode = "400", description = "admin no encontrado")
    })
    public ResponseEntity<AdminUser> getAdminUserByEmail(@PathVariable String email) {
        Optional<AdminUser> adminUser = adminService.getAdminUserByEmail(email);
        return adminUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
