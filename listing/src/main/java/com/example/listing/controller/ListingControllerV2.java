package com.example.listing.controller;

import com.example.listing.assemblers.ListingModelAssembler;
import com.example.listing.model.Listing;
import com.example.listing.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/listings")
public class ListingControllerV2 {

    @Autowired
    private ListingService listingService;

    @Autowired
    private ListingModelAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Listing> getListingById(@PathVariable Long id) {
        Listing listing = listingService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return assembler.toModel(listing);
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Listing>> getAllListingsV2() {
        var listings = listingService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(
            listings,
            linkTo(methodOn(ListingControllerV2.class).getAllListingsV2()).withSelfRel()
        );
    }
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Listing>> createListing(@RequestBody Listing listing, UriComponentsBuilder uriBuilder) {
        Listing saved = listingService.save(listing);

        EntityModel<Listing> model = assembler.toModel(saved);

        return ResponseEntity
            .created(uriBuilder.path("/api/v2/listings/{id}").buildAndExpand(saved.getId()).toUri())
            .body(model);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Listing> updateListing(@PathVariable Long id, @RequestBody Listing listing) {
        Listing existing = listingService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Actualizar campos
        existing.setTitulo(listing.getTitulo());
        existing.setDescripcion(listing.getDescripcion());
        existing.setPrecio(listing.getPrecio());
        existing.setUbicacion(listing.getUbicacion());
        existing.setCategoria(listing.getCategoria());
        existing.setUserId(listing.getUserId());

        Listing updated = listingService.save(existing);
        return assembler.toModel(updated);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteListing(@PathVariable Long id) {
        Listing listing = listingService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        listingService.delete(id);
    }
    @GetMapping(value = "/usuario/{userId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Listing>> getListingsByUserId(@PathVariable Long userId) {
        List<Listing> listings = listingService.findByUserId(userId);
        
        if (listings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay publicaciones para ese usuario");
        }

        List<EntityModel<Listing>> models = listings.stream()
            .map(assembler::toModel)
            .toList();

        return CollectionModel.of(
            models,
            linkTo(methodOn(ListingControllerV2.class).getListingsByUserId(userId)).withSelfRel()
        );
    }
}
