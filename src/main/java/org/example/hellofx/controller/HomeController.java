package org.example.hellofx.controller;


import org.example.hellofx.model.Noticement;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.model.Resident;

import java.util.List;

public interface HomeController {
    /**
     * user clicked the logout button
     */
    public void logoutButtonClicked();

    /**
     * user clicked the change password button
     */
    public void passwordChangeButtonClicked();

    /**
     * user clicked to watch all resident list
     * if user's role is admin or client, show all current resident list
     * if user is a resident, show all resident with the same house_id
     */
    public void danhSachDanCuClicked();

    /**
     * user clicked to see current information
     * if user is a resident, show all resident with the same house_id
     */
    public void thongTinCaNhanClicked();

    /**
     * user clicked to see current information
     * show bill creation form
     */
    public void taoKhoanThuClicked();

    /**
     * show all resient request
     */
    public void hienThiCacYeuCauClicked();

    /**
     * show all bills
     */
    public void danhSachKhoanThuClicked();

    /**
     * show admin all bills
     */
    public void quanLyKhoanThuClicked();

    /**
     * show notification creator scene
     */
    public void taoThongBaoClicked();

    public void quanLyThongBaoClicked();

    public Resident getResident();

    public List<NotificationItem> getNotificationList(Integer residentId, boolean unReadOnly);

    public void noticementClicked(Noticement notice);
}
