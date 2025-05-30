package com.example.listing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.listing.model.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUserId(Long userId);
}