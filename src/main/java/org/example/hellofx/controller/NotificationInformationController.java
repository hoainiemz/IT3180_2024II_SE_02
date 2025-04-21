package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Noticement;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.model.Resident;
import org.example.hellofx.repository.NoticementRepository;
import org.example.hellofx.repository.NotificationItemRepository;
import org.example.hellofx.service.NoticementService;
import org.example.hellofx.service.NotificationService;
import org.example.hellofx.service.ResidentService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationInformationController{
    @Autowired
    private ProfileController profileController;

    @Autowired
    private NoticementService noticementService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ResidentService residentService;

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }

    @Transactional
    public void saveButtonClicked(NotificationItem oldNoti, NotificationItem newNoti, List<Integer> dsIn, List<Integer> dsOut) {
        if (!oldNoti.equals(newNoti)) {
            notificationService.save(newNoti);
        }

        Integer notiId = oldNoti.getId();

        List<Noticement> noticements = dsIn.stream()
                .map(d -> new Noticement(null, notiId, d, Boolean.valueOf(false)))  // Create a Payment object
                .collect(Collectors.toList());

        for (Noticement notice : noticements) {
            notice.setNoticementId(null);
        }
        if (!noticements.isEmpty()) {
            noticementService.saveAll(noticements);
        }
        if (!dsOut.isEmpty()) {
            noticementService.deleteNoticementsByNoticementId(dsOut);
        }
    }

    public void reset(Integer id) {
        JavaFxApplication.showNotificationInformationScene(id);
    }

    public ObservableList<Resident> residentQuery(String query) {
        return FXCollections.observableArrayList(residentService.nativeResidentQuery(query));
    }

    public ObservableList<String> getAllHouseIds(){
        return FXCollections.observableArrayList(residentService.findDistinctNonNullHouseId(getProfile(), getResident()));
    }

    public List<Noticement> getNoticementsById(Integer notiId) {
        return noticementService.findAllByNotificationId(notiId);
    }

    public NotificationItem getNotificationItemById(Integer notiId) {
        return notificationService.findById(notiId);
    }
}
