package org.example.hellofx.controller;

import org.example.hellofx.model.Noticement;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.model.Resident;
import org.example.hellofx.repository.NoticementRepository;
import org.example.hellofx.repository.NotificationItemRepository;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.defaulttheme.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomeController{
    @Autowired
    private ProfileController profileController;
    @Autowired
    private NotificationItemRepository notificationItemRepository;
    @Autowired
    private NoticementRepository noticementRepository;

    private static final int NUM_NOTIES = 20;

    public void logoutButtonClicked(){
        profileController.logOutRequest();
    }

    public void passwordChangeButtonClicked() {
        assert profileController.isLoggedIn();
        JavaFxApplication.showThemeScene(PasswordChangeScene.class);
    }

    public void danhSachDanCuClicked() {
        JavaFxApplication.showThemeScene(ResidentScene.class);
    }

    public void thongTinCaNhanClicked() {
        JavaFxApplication.showUserInformationScene();
    }

    public void taoKhoanThuClicked() {
        JavaFxApplication.showThemeScene(BillCreationScene.class);
    }

    public void hienThiCacYeuCauClicked() {
        JavaFxApplication.showThemeScene(AllResidentRequestScene.class);
    }

    public void danhSachKhoanThuClicked() {
        JavaFxApplication.showThemeScene(BillResidentScene.class);
    }

    public void quanLyKhoanThuClicked() {
        JavaFxApplication.showThemeScene(BillManagementScene.class);
    }

    public void taoThongBaoClicked() {
        JavaFxApplication.showThemeScene(NotificationCreationScene.class);
    }

    public void quanLyThongBaoClicked() {
        JavaFxApplication.showThemeScene(NotificationManagementScene.class);
    }

    public List<NotificationItem> getNotificationList(Integer residentId, boolean unReadOnly) {
//        for (int i = 0; i < 10; i++) {
//
//        }
        return notificationItemRepository.findTopByResidentIdAndWatchedStatusOrderByCreatedAtDesc(residentId, unReadOnly, PageRequest.of(0, 20));
    }

    public Resident getResident() {
        return profileController.getResident();
    }

    public void noticementClicked(Noticement notice) {
        for (int i = 0; i < 10; i++) {
            try {
                noticementRepository.markAsWatched(notice.getNotificationId(), notice.getResidentId());
                break;
            }
            catch (Exception e) {
                continue;
            }
        }
    }
}
