package com.marketplace.Admin.Services;

import com.marketplace.Admin.Model.AdminUser;
import com.marketplace.Admin.Repository.AdminRepository;
import com.marketplace.Admin.Service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAdminUsers() {
        List<AdminUser> adminList = List.of(new AdminUser(), new AdminUser());
        when(adminRepository.findAll()).thenReturn(adminList);

        List<AdminUser> result = adminService.getAllAdminUsers();
        assertEquals(2, result.size());
    }

    @Test
    void testCreateAdminUser() {
        AdminUser user = new AdminUser();
        user.setEmail("admin@test.com");

        when(adminRepository.save(user)).thenReturn(user);

        AdminUser result = adminService.createAdminUser(user);
        assertEquals("admin@test.com", result.getEmail());
    }
}