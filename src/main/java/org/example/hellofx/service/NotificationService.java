package org.example.hellofx.service;

import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.repository.NotificationItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationService {
    @Autowired
    private NotificationItemRepository notificationItemRepository;

    public NotificationItem findById(Integer id) {
        return notificationItemRepository.findById(id).get();
    }

    public List<NotificationItem> findNotifications(String typeFilter, String searchFIlter) {
        return notificationItemRepository.findNotifications(typeFilter, searchFIlter);
    }

    public List<NotificationItem> findTopByResidentIdAndWatchedStatusOrderByCreatedAtDesc(Integer residentId, Boolean unReadOnly, Pageable pageable) {
        return notificationItemRepository.findTopByResidentIdAndWatchedStatusOrderByCreatedAtDesc(residentId, unReadOnly, PageRequest.of(0, 20));
    }

    public NotificationItem save(NotificationItem notificationItem) {
        return notificationItemRepository.save(notificationItem);
    }

    public void deleteNotificationById(Integer id) {
        notificationItemRepository.deleteByNotificationId(id);
    }

    public List<NotificationItem> findAll() {
        return notificationItemRepository.findAll();
    }
}
