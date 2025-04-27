package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import org.example.hellofx.SpringBootFxApplication;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Resident;
import org.example.hellofx.service.BillService;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.defaulttheme.ApartmentCreationScene;
import org.example.hellofx.ui.theme.defaulttheme.BillCreationScene;
import org.example.hellofx.ui.theme.defaulttheme.BillInformationScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillManagerController{
    @Autowired
    private ProfileController profileController;

    @Autowired
    private BillService billService;

    public Resident getResident() {
        return profileController.getResident();
    }

    public Account getProfile() {
        return profileController.getProfile();
    }

    public void seeBillInformation(Integer billId) {
        JavaFxApplication.showBillInformationScene(billId);
    }

    public ObservableList<Bill> getBills(int requireFilter, int dueFilter, String searchFilter) {
        return FXCollections.observableArrayList(billService.findBillsByFilters(requireFilter, dueFilter, searchFilter));
    }

    public Scene getBillCreationScene(Scene scene) {
        return SpringBootFxApplication.context.getBean(BillCreationScene.class).getScene(scene);
    }

    public Scene getBillInfoScene(Scene scene, Integer billId) {
        BillInformationScene theme = SpringBootFxApplication.context.getBean(BillInformationScene.class);
        return theme.getScene(billId, scene);
    }
}
