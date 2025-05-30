package com.moderation.cl.moderation.service;

import com.moderation.cl.moderation.model.UserModeration;
import com.moderation.cl.moderation.repository.UserModerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserModerationService {

    @Autowired
    private UserModerationRepository userModerationRepository;

    public List<UserModeration> findAll() {
        return userModerationRepository.findAll();
    }

    public Optional<UserModeration> findById(Long id) {
        return userModerationRepository.findById(id);
    }

    public UserModeration save(UserModeration userModeration) {
        return userModerationRepository.save(userModeration);
    }

    public void deleteById(Long id) {
        userModerationRepository.deleteById(id);
    }
}