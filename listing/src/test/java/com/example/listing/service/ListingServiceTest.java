package com.example.listing.service;

import com.example.listing.model.Listing;
import com.example.listing.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingService listingService;

    private Listing listing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listing = new Listing(1L, "Ejemplo", "Descripción", new BigDecimal("10000"), "Santiago", "Electrónica", 10L);
    }

    @Test
    void testFindAll() {
        when(listingRepository.findAll()).thenReturn(List.of(listing));
        List<Listing> result = listingService.findAll();
        assertEquals(1, result.size());
        assertEquals("Ejemplo", result.get(0).getTitulo());
    }

    @Test
    void testFindById() {
        when(listingRepository.findById(1L)).thenReturn(Optional.of(listing));
        Optional<Listing> result = listingService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Ejemplo", result.get().getTitulo());
    }

    @Test
    void testSave() {
        when(listingRepository.save(listing)).thenReturn(listing);
        Listing result = listingService.save(listing);
        assertNotNull(result);
        assertEquals("Ejemplo", result.getTitulo());
    }

    @Test
    void testDelete() {
        doNothing().when(listingRepository).deleteById(1L);
        listingService.delete(1L);
        verify(listingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByUserId() {
        when(listingRepository.findByUserId(10L)).thenReturn(List.of(listing));
        List<Listing> result = listingService.findByUserId(10L);
        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getUserId());
    }
}
