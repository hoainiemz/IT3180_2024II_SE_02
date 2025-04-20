package org.example.hellofx.controller;

import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.stereotype.Component;

@Component
public class NotificationManagementController{
    public void seeNotificationItemInformation(Integer notiId) {
        JavaFxApplication.showNotificationInformationScene(notiId);
    }
}
