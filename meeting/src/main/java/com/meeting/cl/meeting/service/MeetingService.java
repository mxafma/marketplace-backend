package com.meeting.cl.meeting.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.cl.meeting.model.Meeting;
import com.meeting.cl.meeting.repository.MeetingRepository;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository repository;

    public List<Meeting> getAllMeetings() {
        return repository.findAll();
    }

    public Meeting saveMeeting(Meeting meeting) {
        return repository.save(meeting);
    }

    public Optional<Meeting> getMeetingById(Long id) {
        return repository.findById(id);
    }

    public Meeting updateMeeting(Long id, Meeting meeting) {
        meeting.setId(id);
        return repository.save(meeting);
    }

    public void deleteMeeting(Long id) {
        repository.deleteById(id);
    }

    public List<Meeting> getMeetingsByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
