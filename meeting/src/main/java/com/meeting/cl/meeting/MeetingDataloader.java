package com.meeting.cl.meeting;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.meeting.cl.meeting.model.Meeting;
import com.meeting.cl.meeting.repository.MeetingRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class MeetingDataloader implements CommandLineRunner {

    @Autowired
    private MeetingRepository meetingRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        String[] estados = {"pendiente", "confirmada", "cancelada", "completada"};

        for (int i = 0; i < 20; i++) {
            Meeting Meeting = new Meeting();
            Meeting.setUsuarioId((long) faker.number().numberBetween(1, 10));
            Meeting.setVendedorId((long) faker.number().numberBetween(1, 10));
            Meeting.setFecha(LocalDateTime.now().plusDays(faker.number().numberBetween(1, 30)));
            Meeting.setCodigoSeguridad(faker.regexify("SEC[0-9]{4}"));
            Meeting.setLugarEncuentro(faker.address().streetAddress());
            Meeting.setEstado(estados[random.nextInt(estados.length)]);

            meetingRepository.save(Meeting);
        }
    }
}
