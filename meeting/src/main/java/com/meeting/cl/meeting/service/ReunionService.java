package com.meeting.cl.meeting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.cl.meeting.model.Reunion;
import com.meeting.cl.meeting.repository.ReunionRepository;

@Service
public class ReunionService {

    @Autowired
    private ReunionRepository repository;

    public List<Reunion> getAllReuniones() {
        return repository.findAll();
    }

    public Reunion saveReunion(Reunion reunion) {
        return repository.save(reunion);
    }
}
