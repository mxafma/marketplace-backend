package com.meeting.cl.meeting.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.meeting.cl.meeting.model.Reunion;

public interface ReunionRepository extends JpaRepository<Reunion, Long> {
}
