package org.example.hellofx.service;

import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.repository.NotificationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationItemRepository notificationItemRepository;

    public NotificationItem findById(Integer id) {
        return notificationItemRepository.findById(id).get();
    }

    public List<NotificationItem> findNotifications(String typeFilter, String searchFIlter) {
        return notificationItemRepository.findNotifications(typeFilter, searchFIlter);
    }
}
