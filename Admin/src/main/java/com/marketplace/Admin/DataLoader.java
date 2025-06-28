package com.marketplace.Admin;

import com.marketplace.Admin.Model.AdminUser;
import com.marketplace.Admin.Repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public void run(String... args) throws Exception {
    Faker faker = new Faker();
    Random random = new Random();


    for (int i = 0; i < 20; i++) {
    AdminUser adminUser = new AdminUser();
    adminUser.setSubject("Admin " + (i + 1));
    adminUser.setDescription(faker.lorem().sentence(8));
    adminUser.setEmail(faker.internet().emailAddress());
    adminUser.setPassword("admin123"); 
    adminRepository.save(adminUser);

   
    }
    }
}



