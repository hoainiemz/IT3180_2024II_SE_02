package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Resident;
import org.example.hellofx.repository.BillRepository;
import org.example.hellofx.repository.PaymentRepository;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.defaulttheme.BillCreationScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//import static java.util.stream.Nodes.collect;

@Component
public class BillCreationController{
    @Autowired
    private ProfileController profileController;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private  PaymentRepository paymentRepository;
    @Autowired
    private DataBaseService dataBaseService;

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }


    @Transactional
    public void createButtonClicked(Bill bill, List<Integer> residentIds) {
        bill = billRepository.save(bill);
        Integer billId = bill.getBillId();
        List<Payment> payments = residentIds.stream()
                .map(d -> new Payment(null, billId, d, null))  // Create a Payment object
                .collect(Collectors.toList());

        for (Payment p : payments) {
            p.setPaymentId(null); // Ensure new inserts
        }
        dataBaseService.saveAllPayments(payments);

    }

    public void reset() {
        JavaFxApplication.showThemeScene(BillCreationScene.class);
    }
}
