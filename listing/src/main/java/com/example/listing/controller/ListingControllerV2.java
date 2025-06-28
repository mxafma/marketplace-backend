package com.example.listing.controller;

import com.example.listing.assemblers.ListingModelAssembler;
import com.example.listing.model.Listing;
import com.example.listing.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v2/listings")
public class ListingControllerV2 {

    @Autowired
    private ListingService listingService;

    @Autowired
    private ListingModelAssembler assembler;

    @GetMapping("/{id}")
    public EntityModel<Listing> getById(@PathVariable Long id) {
        Listing listing = listingService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return assembler.toModel(listing);
    }
}
