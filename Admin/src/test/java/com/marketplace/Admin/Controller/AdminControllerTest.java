package com.marketplace.Admin.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.Admin.Model.AdminUser;
import com.marketplace.Admin.Service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private AdminUser sampleAdmin;

@BeforeEach
void setUp() {
    sampleAdmin = new AdminUser();
    sampleAdmin.setIdAdmin(1L);
    sampleAdmin.setSubject("Admin Subject");
    sampleAdmin.setDescription("Admin Description");
    sampleAdmin.setEmail("admin@test.com");
    sampleAdmin.setPassword("1234");
}

    @Test
    void testGetAllAdminUsers() throws Exception {
        List<AdminUser> admins = List.of(sampleAdmin);
        when(adminService.getAllAdminUsers()).thenReturn(admins);

        mockMvc.perform(get("/api/admin-users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("admin@test.com"));
    }

    @Test
    void testGetAdminUserById_Found() throws Exception {
        when(adminService.getAdminUserById(1L)).thenReturn(Optional.of(sampleAdmin));

        mockMvc.perform(get("/api/admin-users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@test.com"));
    }

    @Test
    void testGetAdminUserById_NotFound() throws Exception {
        when(adminService.getAdminUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/admin-users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateAdminUser() throws Exception {
        when(adminService.createAdminUser(any(AdminUser.class))).thenReturn(sampleAdmin);

        mockMvc.perform(post("/api/admin-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleAdmin)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("admin@test.com"));
    }

    @Test
    void testUpdateAdminUser_Found() throws Exception {
        when(adminService.updateAdminUser(Mockito.eq(1L), any(AdminUser.class))).thenReturn(sampleAdmin);

        mockMvc.perform(put("/api/admin-users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleAdmin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@test.com"));
    }

    @Test
    void testUpdateAdminUser_NotFound() throws Exception {
        when(adminService.updateAdminUser(Mockito.eq(1L), any(AdminUser.class))).thenReturn(null);

        mockMvc.perform(put("/api/admin-users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sampleAdmin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAdminUser() throws Exception {
        mockMvc.perform(delete("/api/admin-users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAdminUserByEmail_Found() throws Exception {
        when(adminService.getAdminUserByEmail("admin@test.com")).thenReturn(Optional.of(sampleAdmin));

        mockMvc.perform(get("/api/admin-users/email/admin@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@test.com"));
    }

    @Test
    void testGetAdminUserByEmail_NotFound() throws Exception {
        when(adminService.getAdminUserByEmail("admin@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/admin-users/email/admin@test.com"))
                .andExpect(status().isNotFound());
    }

    // Utilidad para convertir objetos a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}