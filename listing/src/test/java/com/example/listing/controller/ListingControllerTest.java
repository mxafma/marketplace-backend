package com.example.listing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.listing.model.Listing;
import com.example.listing.service.ListingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ListingController.class)
public class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListingService listingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Listing listing;

    @BeforeEach
    void setUp() { 
        listing = new Listing();
        listing.setId(1L); //L porque es long
        listing.setTitulo("Producto prueba");
        listing.setDescripcion("Descripción de prueba");
        listing.setPrecio(new BigDecimal("19990"));
        listing.setUbicacion("Santiago");
        listing.setCategoria("Electrónica");
        listing.setUserId(10L);
    }

    @Test
    public void testGetAllListings() throws Exception { //get todas las publicaciones 
        when(listingService.findAll()).thenReturn(List.of(listing));

        mockMvc.perform(get("/api/listings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Producto prueba"));
    }

    @Test
    public void testGetListingById() throws Exception { //get publicacion id
        when(listingService.findById(1L)).thenReturn(Optional.of(listing));

        mockMvc.perform(get("/api/listings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Producto prueba"));
    }

    @Test
    public void testCreateListing() throws Exception {// crear publicacion post
        when(listingService.save(any(Listing.class))).thenReturn(listing);

        mockMvc.perform(post("/api/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Producto prueba"));
    }

    @Test
    public void testUpdateListing() throws Exception { //modificar publicacion put
        when(listingService.save(any(Listing.class))).thenReturn(listing);

        mockMvc.perform(put("/api/listings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Producto prueba"));
    }

    @Test
    public void testDeleteListing() throws Exception { //borrar publicacion 
        doNothing().when(listingService).delete(1L);

        mockMvc.perform(delete("/api/listings/1"))
                .andExpect(status().isOk());

        verify(listingService, times(1)).delete(1L);
    }
    @Test
    public void testGetListingsByUserId() throws Exception {
        // Simular que el servicio devuelve una lista con 1 publicación
        when(listingService.findByUserId(1L)).thenReturn(List.of(listing));

        mockMvc.perform(get("/api/listings/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Producto prueba"))
                .andExpect(jsonPath("$[0].descripcion").value("Descripción de prueba"))
                .andExpect(jsonPath("$[0].precio").value(19990))
                .andExpect(jsonPath("$[0].ubicacion").value("Santiago"))
                .andExpect(jsonPath("$[0].categoria").value("Electrónica"))
                .andExpect(jsonPath("$[0].userId").value(10));
    }

}
