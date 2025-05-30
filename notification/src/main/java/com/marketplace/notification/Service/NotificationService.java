package com.marketplace.notification.Service;

import com.marketplace.notification.Model.Notification;
import com.marketplace.notification.Repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Crear una notificación
    public Notification createNotification(Notification notification) {
        notification.setTimestamp(LocalDateTime.now());
        notification.setStatus("SENT");  
        return notificationRepository.save(notification);
    }

    // Obtener todas las notificaciones
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Obtener notificaciones por usuario
    public List<Notification> getNotificationsByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    // Obtener notificaciones por tipo
    public List<Notification> getNotificationsByType(String type) {
        return notificationRepository.findByType(type);
    }


}
