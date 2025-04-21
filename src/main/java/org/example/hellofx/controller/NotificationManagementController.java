package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.repository.NotificationItemRepository;
import org.example.hellofx.service.NotificationService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationManagementController{
    NotificationService notificationService;

    public void seeNotificationItemInformation(Integer notiId) {
        JavaFxApplication.showNotificationInformationScene(notiId);
    }

    public ObservableList<NotificationItem> getNotifications(String typeFilter, String searchFilter) {
        return FXCollections.observableArrayList(notificationService.findNotifications(typeFilter, searchFilter));
    }
}
