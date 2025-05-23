package com.meeting.cl.meeting.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meeting.cl.meeting.model.Reunion;
import com.meeting.cl.meeting.service.ReunionService;

@RestController
@RequestMapping("/api/meetings")
public class ReunionController {

    @Autowired
    private ReunionService reunionService;

    @GetMapping
    public List<Reunion> getAll() {
        return reunionService.getAllReuniones();
    }

    @PostMapping
    public Reunion create(@RequestBody Reunion reunion) {
        return reunionService.saveReunion(reunion);
    }
}

