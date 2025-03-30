package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.controller.NotificationInformationController;
import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.model.*;
import org.example.hellofx.repository.NoticementRepository;
import org.example.hellofx.repository.NotificationItemRepository;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultNotificationInformationController implements NotificationInformationController {
    @Autowired
    private ProfileController profileController;
    @Autowired
    private NoticementRepository noticementRepository;
    @Autowired
    private NotificationItemRepository notificationItemRepository;

    @Override
    public Account getProfile() {
        return profileController.getProfile();
    }

    @Override
    public Resident getResident() {
        return profileController.getResident();
    }

    @Transactional
    public void saveButtonClicked(NotificationItem oldNoti, NotificationItem newNoti, List<Integer> dsIn, List<Integer> dsOut) {
        if (!oldNoti.equals(newNoti)) {
            notificationItemRepository.save(newNoti);
        }

        Integer notiId = oldNoti.getId();

        List<Noticement> noticements = dsIn.stream()
                .map(d -> new Noticement(null, notiId, d, Boolean.valueOf(false)))  // Create a Payment object
                .collect(Collectors.toList());

        for (Noticement notice : noticements) {
            notice.setNoticementId(null);
        }
        if (!noticements.isEmpty()) {
            noticementRepository.saveAll(noticements);
        }
        if (!dsOut.isEmpty()) {
            noticementRepository.deleteNoticementsByNoticementId(dsOut);
        }
    }

    public void reset(Integer id) {
        JavaFxApplication.showNotificationInformationScene(id);
    }
}
