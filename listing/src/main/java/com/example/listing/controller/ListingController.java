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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/listings")
@Tag(name = "Publicaciones", description = "Operaciones relacionadas con las publicaciones del marketplace")
public class ListingController {

    @Autowired
    private ListingService listingService;


    @GetMapping
    @Operation(summary = "Obtener todas las publicaciones", description = "Devuelve una lista de todas las publicaciones disponibles")
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Listing.class)))
    public List<Listing> getAll() {
        return listingService.findAll();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener una publicación por ID(de la publicacion)", description = "Devuelve los datos de una publicación específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicación encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class))),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
    })
    public Optional<Listing> getById(@PathVariable Long id) {
        return listingService.findById(id);
    }


    @PostMapping
    @Operation(summary = "Insertar nueva Publicación", description = "Crea una nueva publicación en el sistema")
    @ApiResponse(responseCode = "200", description = "Publicación creada e insertada exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Listing.class)))

    public Listing create(@RequestBody Listing listing) {
        return listingService.save(listing);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar publicación por su ID", description = "Actualiza los datos de una publicación especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicación actualizada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Listing.class))),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
    })
    public Listing update(@PathVariable Long id, @RequestBody Listing listing) {
        listing.setId(id); 
        return listingService.save(listing);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una publicación Por su ID", description = "Elimina una publicación específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicación eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
    })
    public void delete(@PathVariable Long id) {
        listingService.delete(id);
    }


    @GetMapping("/usuario/{userId}") //encuentra publicaciones por usuario
    @Operation(summary = "Obtener publicaciones por usuario", description = "Devuelve todas las publicaciones realizadas por un usuario específico")
    @ApiResponse(responseCode = "200", description = "Listado de publicaciones del usuario obtenido",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Listing.class)))

    public List<Listing> getByUserId(@PathVariable Long userId) {
        return listingService.findByUserId(userId);
    }
}