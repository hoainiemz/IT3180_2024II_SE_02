package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.controller.HomeController;
import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.model.Noticement;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Resident;
import org.example.hellofx.repository.NoticementRepository;
import org.example.hellofx.repository.NotificationItemRepository;
import org.example.hellofx.repository.PaymentRepository;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultHomeController implements HomeController {
    @Autowired
    private ProfileController profileController;
    @Autowired
    private NotificationItemRepository notificationItemRepository;

    private static final int NUM_NOTIES = 20;
    @Autowired
    private NoticementRepository noticementRepository;

    public void logoutButtonClicked(){
        profileController.logOutRequest();
    }

    public void passwordChangeButtonClicked() {
        assert profileController.isLoggedIn();
        JavaFxApplication.showPasswordChangeScene();
    }

    @Override
    public void danhSachDanCuClicked() {
        JavaFxApplication.showResidentListScene();
    }

    @Override
    public void thongTinCaNhanClicked() {
        JavaFxApplication.showUserInformationScene();
    }

    public void taoKhoanThuClicked() {
        JavaFxApplication.showBilCreationScene();
    }

    public void hienThiCacYeuCauClicked() {
        JavaFxApplication.showAllResidentRequestScene();
    }

    public void danhSachKhoanThuClicked() {
        JavaFxApplication.showBillScene();
    }

    public void quanLyKhoanThuClicked() {
        JavaFxApplication.showBillManagerScene();
    }

    public void taoThongBaoClicked() {
        JavaFxApplication.showNotificationCreationScene();
    }

    public void quanLyThongBaoClicked() {
        JavaFxApplication.showNotificationManagementScene();
    }

    public List<NotificationItem> getNotificationList(Integer residentId, boolean unReadOnly) {
//        for (int i = 0; i < 10; i++) {
//
//        }
        return notificationItemRepository.findTopByResidentIdAndWatchedStatusOrderByCreatedAtDesc(residentId, unReadOnly, PageRequest.of(0, 20));
    }

    @Override
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
