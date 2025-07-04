package com.meeting.cl.meeting.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meeting.cl.meeting.model.Meeting;


public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findByUsuarioId(Long usuarioId);
    

}
