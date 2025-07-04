package com.marketplace.Admin.Controller;


import com.marketplace.Admin.Model.AdminUser;
import com.marketplace.Admin.Service.AdminService;
import com.marketplace.Admin.assemblers.AdminModelAssembler;

import lombok.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin-usersV2")
public class AdminControllerV2 {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminModelAssembler assembler;
    

    // Obtener todos los administradores
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<AdminUser>> getAllAdminUsersV2() {
        var admins = adminService.getAllAdminUsers().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(
            admins,
            linkTo(methodOn(AdminControllerV2.class).getAllAdminUsersV2()).withSelfRel()
            );
    }

    // Obtener un administrador por ID
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<AdminUser> getAdminUserById(@PathVariable Long id) {
        AdminUser adminUser = adminService.getAdminUserById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return assembler.toModel(adminUser);
    }

    // Crear un nuevo administrador
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<AdminUser>> createAdminUser(@RequestBody AdminUser adminUser, UriComponentsBuilder uriBuilder) {
        AdminUser saved = adminService.createAdminUser(adminUser);

        EntityModel<AdminUser> model = assembler.toModel(saved);

        return ResponseEntity
            .created(uriBuilder.path("/api/v2/admins/{id}").buildAndExpand(saved.getIdAdmin()).toUri())
            .body(model);
    }

    // Actualizar un administrador
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<AdminUser> updateAdminUser(@PathVariable Long id, @RequestBody AdminUser adminUser) {
        AdminUser existing = adminService.getAdminUserById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    // Actualizar solo los campos modificables
    existing.setDescription(adminUser.getDescription());
    existing.setEmail(adminUser.getEmail());
    existing.setPassword(adminUser.getPassword());
    existing.setSubject(adminUser.getSubject());

    AdminUser update = adminService.createAdminUser(existing); // posiblemente deberías tener un método update, no "create"
    return assembler.toModel(update);
}

    // Eliminar un administrador2
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdmin(@PathVariable Long id) {
        AdminUser adminUser = adminService.getAdminUserById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        adminService.deleteAdminUser(id);
        
    }

    // Buscar un administrador por email
    @GetMapping(value = "/email/{email}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<AdminUser> getAdminUserByEmail(@PathVariable String email) {
    AdminUser adminUser = adminService.getAdminUserByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un administrador con ese email"));

    return assembler.toModel(adminUser);
}

}
