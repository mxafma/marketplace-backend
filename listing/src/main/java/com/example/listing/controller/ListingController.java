package com.example.listing.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.listing.model.Listing;
import com.example.listing.service.ListingService;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @GetMapping
    public List<Listing> getAll() {
        return listingService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Listing> getById(@PathVariable Long id) {
        return listingService.findById(id);
    }

    @PostMapping
    public Listing create(@RequestBody Listing listing) {
        return listingService.save(listing);
    }
    @PutMapping("/{id}")
    public Listing update(@PathVariable Long id, @RequestBody Listing listing) {
        listing.setId(id); 
        return listingService.save(listing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        listingService.delete(id);
    }
    @GetMapping("/usuario/{userId}") //encuentra publicaciones por usuario
    public List<Listing> getByUserId(@PathVariable Long userId) {
        return listingService.findByUserId(userId);
    }
}