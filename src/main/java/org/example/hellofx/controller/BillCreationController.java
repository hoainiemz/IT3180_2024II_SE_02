package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Resident;
import org.example.hellofx.service.BillService;
import org.example.hellofx.service.PaymentService;
import org.example.hellofx.service.ResidentService;
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
    private BillService billService;
    @Autowired
    private ResidentService residentService;
    @Autowired
    private PaymentService paymentService;

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }


    @Transactional
    public void createButtonClicked(Bill bill, List<Integer> residentIds) {
        bill = billService.save(bill);
        Integer billId = bill.getBillId();
        List<Payment> payments = residentIds.stream()
                .map(d -> new Payment(null, billId, d, null))  // Create a Payment object
                .collect(Collectors.toList());

        for (Payment p : payments) {
            p.setPaymentId(null); // Ensure new inserts
        }
        paymentService.saveAllPayments(payments);

    }

    public void reset() {
        JavaFxApplication.showThemeScene(BillCreationScene.class);
    }

    public ObservableList<Resident> residentQuery(String query) {
        return FXCollections.observableArrayList(residentService.nativeResidentQuery(query));
    }

    public ObservableList<String> getAllHouseIds(){
        return FXCollections.observableArrayList(residentService.findDistinctNonNullHouseId(getProfile(), getResident()));
    }
}
