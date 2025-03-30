package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.controller.NotificationCreationController;
import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.model.*;
import org.example.hellofx.repository.NoticementRepository;
import org.example.hellofx.repository.NotificationItemRepository;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.NotificationCreationScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultNotificationCreationController implements NotificationCreationController {
    @Autowired
    private ProfileController profileController;
    @Autowired
    private NotificationItemRepository notificationItemRepository;
    @Autowired
    private NoticementRepository noticementRepository;

    @Override
    public Resident getResident() {
        return profileController.getResident();
    }

    @Override
    public Account getProfile() {
        return profileController.getProfile();
    }

    public void reset() {
        JavaFxApplication.showNotificationCreationScene();
    }

    @Override
    @Transactional
    public void createNotificationClicked(NotificationItem notification, List<Integer> ds) {
//        System.out.println(notification.getTitle() + "\n" + notification.getMessage() + "\n" + notification.getType() + "\n" + notification.getCreatedAt());
        int numTries = 10;
        NotificationItem noti = null;
        for (int i = 0; i < numTries; i++) {
            try {
                noti = notificationItemRepository.save(notification);
                break;
            }
            catch (Exception e) {
                continue;
            }
        }
        assert  noti != null;
        Integer notiId = noti.getId();
        List<Noticement> noticements = ds.stream()
                .map(d -> new Noticement(null, notiId, d, false))
                .collect(Collectors.toList());
        for (int i = 0; i < 10; i++) {
            try {
                noticementRepository.saveAll(noticements);
                return;
            }
            catch (Exception e) {
                continue;
            }
        }
    }
}
