package com.meeting.cl.meeting.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.meeting.cl.meeting.model.Meeting;
import com.meeting.cl.meeting.repository.MeetingRepository;

class MeetingServiceTest {

    @Mock
    private MeetingRepository repository;

    @InjectMocks
    private MeetingService meetingService;

    private Meeting meeting;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        meeting = new Meeting();
        meeting.setId(1L);
        meeting.setUsuarioId(2L);
        meeting.setVendedorId(3L);
        meeting.setFecha(LocalDateTime.now());
        meeting.setCodigoSeguridad("SEC1234");
        meeting.setLugarEncuentro("Lugar");
        meeting.setEstado("pendiente");
    }

    @Test
    void testGetAllMeetings() {
        when(repository.findAll()).thenReturn(List.of(meeting));
        List<Meeting> result = meetingService.getAllMeetings();
        assertEquals(1, result.size());
        assertEquals(meeting.getId(), result.get(0).getId());
    }

    @Test
    void testSaveMeeting() {
        when(repository.save(meeting)).thenReturn(meeting);
        Meeting result = meetingService.saveMeeting(meeting);
        assertNotNull(result);
        assertEquals(meeting.getId(), result.getId());
    }

    @Test
    void testGetMeetingById_found() {
        when(repository.findById(1L)).thenReturn(Optional.of(meeting));
        Optional<Meeting> result = meetingService.getMeetingById(1L);
        assertTrue(result.isPresent());
        assertEquals(meeting.getId(), result.get().getId());
    }

    @Test
    void testGetMeetingById_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Optional<Meeting> result = meetingService.getMeetingById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateMeeting() {
        when(repository.save(meeting)).thenReturn(meeting);
        Meeting result = meetingService.updateMeeting(1L, meeting);
        assertNotNull(result);
        assertEquals(meeting.getId(), result.getId());
    }

    @Test
    void testDeleteMeeting() {
        doNothing().when(repository).deleteById(1L);
        meetingService.deleteMeeting(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testGetMeetingsByUsuarioId() {
        when(repository.findByUsuarioId(2L)).thenReturn(List.of(meeting));
        List<Meeting> result = meetingService.getMeetingsByUsuarioId(2L);
        assertEquals(1, result.size());
        assertEquals(meeting.getUsuarioId(), result.get(0).getUsuarioId());
    }
}
