package com.example.listing;

import com.example.listing.model.Listing;
import com.example.listing.repository.ListingRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Profile("dev")
@Component
public class ListingDataLoader implements CommandLineRunner {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            Listing listing = new Listing();
            listing.setTitulo(faker.commerce().productName());
            listing.setDescripcion(faker.lorem().paragraph());
            listing.setPrecio(BigDecimal.valueOf(faker.number().randomDouble(2, 10000, 99999)));
            listing.setUbicacion(faker.address().cityName());
            listing.setCategoria(faker.commerce().department());
            listing.setUserId((long) faker.number().numberBetween(1, 10));

            listingRepository.save(listing);
        }
    }
}
