package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Resident;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillInformationController{
    @Autowired
    private ProfileController profileController;
    @Autowired
    private DataBaseService dataBaseService;

    public Resident getResident() {
        return profileController.getResident();
    }

    public Account getProfile() {
        return profileController.getProfile();
    }

    public void saveButtonClicked(Bill oldBill, Bill bill, List<Integer> dsIn, List<Integer> dsOut) {
        if (!bill.equals(oldBill)) {
            dataBaseService.updateBill(bill);
        }
        Integer billId = bill.getBillId();
        List<Payment> payments = dsIn.stream()
                .map(d -> new Payment(null, billId, d, null))  // Create a Payment object
                .collect(Collectors.toList());

        for (Payment p : payments) {
            p.setPaymentId(null); // Ensure new inserts
        }
        if (!payments.isEmpty()) {
            dataBaseService.saveAllPayments(payments);
        }
        if (!dsOut.isEmpty()) {
            dataBaseService.deletePayments(dsOut);
        }

        System.out.println("save button clicked");
    }

    public void reset(Bill bill) {
        JavaFxApplication.showBillInformationScene(bill.getBillId());
    }
}
