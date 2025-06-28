package com.example.listing.assemblers;

import com.example.listing.controller.ListingControllerV2;
import com.example.listing.model.Listing;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ListingModelAssembler implements RepresentationModelAssembler<Listing, EntityModel<Listing>> {

    @Override
    public EntityModel<Listing> toModel(Listing listing) {
        return EntityModel.of(listing,
            linkTo(methodOn(ListingControllerV2.class).getListingById(listing.getId())).withSelfRel(),
            linkTo(methodOn(ListingControllerV2.class).getAllListingsV2()).withRel("listings")
        );
    }
}
