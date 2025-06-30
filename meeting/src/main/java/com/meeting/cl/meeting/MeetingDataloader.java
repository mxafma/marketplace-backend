package com.meeting.cl.meeting;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.meeting.cl.meeting.model.Reunion;
import com.meeting.cl.meeting.repository.ReunionRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class MeetingDataloader implements CommandLineRunner {

    @Autowired
    private ReunionRepository reunionRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        String[] estados = {"pendiente", "confirmada", "cancelada", "completada"};

        for (int i = 0; i < 20; i++) {
            Reunion reunion = new Reunion();
            reunion.setUsuarioId((long) faker.number().numberBetween(1, 10));
            reunion.setVendedorId((long) faker.number().numberBetween(1, 10));
            reunion.setFecha(LocalDateTime.now().plusDays(faker.number().numberBetween(1, 30)));
            reunion.setCodigoSeguridad(faker.regexify("SEC[0-9]{4}"));
            reunion.setLugarEncuentro(faker.address().streetAddress());
            reunion.setEstado(estados[random.nextInt(estados.length)]);

            reunionRepository.save(reunion);
        }
    }
}
