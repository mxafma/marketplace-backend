package com.moderation.cl.moderation.repository;

import com.moderation.cl.moderation.model.UserModeration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModerationRepository extends JpaRepository<UserModeration, Long> {
    
}