package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.controller.NotificationManagementController;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.stereotype.Component;

@Component
public class DefaultNotificationManagementController implements NotificationManagementController {
    @Override
    public void seeNotificationItemInformation(Integer notiId) {
        JavaFxApplication.showNotificationInformationScene(notiId);
    }
}
