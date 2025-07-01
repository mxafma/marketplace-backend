package com.meeting.cl.meeting.controller;

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
import com.meeting.cl.meeting.service.MeetingService;

class MeetingControllerTest {

    @Mock
    private MeetingService meetingService;

    @InjectMocks
    private MeetingController controller;

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
    void testGetById_found() {
        when(meetingService.getMeetingById(1L)).thenReturn(Optional.of(meeting));
        Optional<Meeting> result = controller.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(meeting.getId(), result.get().getId());
    }

    @Test
    void testGetById_notFound() {
        when(meetingService.getMeetingById(1L)).thenReturn(Optional.empty());
        Optional<Meeting> result = controller.getById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAll() {
        when(meetingService.getAllMeetings()).thenReturn(List.of(meeting));
        List<Meeting> result = controller.getAll();
        assertEquals(1, result.size());
    }

    @Test
    void testCreate() {
        when(meetingService.saveMeeting(meeting)).thenReturn(meeting);
        Meeting result = controller.create(meeting);
        assertNotNull(result);
        assertEquals(meeting.getId(), result.getId());
    }

    @Test
    void testUpdate() {
        when(meetingService.updateMeeting(1L, meeting)).thenReturn(meeting);
        Meeting result = controller.update(1L, meeting);
        assertNotNull(result);
        assertEquals(meeting.getId(), result.getId());
    }

    @Test
    void testDelete() {
        doNothing().when(meetingService).deleteMeeting(1L);
        controller.delete(1L);
        verify(meetingService, times(1)).deleteMeeting(1L);
    }

    @Test
    void testGetByUsuarioId() {
        when(meetingService.getMeetingsByUsuarioId(2L)).thenReturn(List.of(meeting));
        List<Meeting> result = controller.getByUsuarioId(2L);
        assertEquals(1, result.size());
    }
}
