package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Resident;
import org.example.hellofx.service.BillService;
import org.example.hellofx.ui.JavaFxApplication;
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
}
