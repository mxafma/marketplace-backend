package com.example.listing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.listing.model.Listing;
import com.example.listing.repository.ListingRepository;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    public Optional<Listing> findById(Long id) {
        return listingRepository.findById(id);
    }

    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }

    public void delete(Long id) {
        listingRepository.deleteById(id);
    }
}