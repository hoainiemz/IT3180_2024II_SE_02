package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillResidentController {
    @Autowired
    private ProfileController profileController;
    @Autowired
    private PaymentService paymentService;

    public Resident getResident() {
        return profileController.getResident();
    }

    public Account getProfile() {
        return profileController.getProfile();
    }

    public ObservableList<ResidentBillPaymentDTO> getPayment(Integer residentId, int stateFilter, int requireFilter, int dueFilter, String searchFilter) {
        return FXCollections.observableArrayList(paymentService.findPaymentByResidentFilters(residentId, stateFilter, requireFilter, dueFilter, searchFilter));
    }
}
