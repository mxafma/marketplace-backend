package com.moderation.cl.moderation.controller;

import com.moderation.cl.moderation.model.UserModeration;
import com.moderation.cl.moderation.service.UserModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-moderation")
public class UserModerationController {

    @Autowired
    private UserModerationService userModerationService;

    @GetMapping
    public List<UserModeration> getAll() {
        return userModerationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModeration> getById(@PathVariable Long id) {
        Optional<UserModeration> user = userModerationService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserModeration create(@RequestBody UserModeration userModeration) {
        return userModerationService.save(userModeration);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userModerationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}