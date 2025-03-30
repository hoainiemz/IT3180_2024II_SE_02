package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.controller.BillCreationController;
import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Resident;
import org.example.hellofx.repository.BillRepository;
import org.example.hellofx.repository.PaymentRepository;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.service.databaseservice.SupabaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//import static java.util.stream.Nodes.collect;

@Component
public class DefaultBillCreationController implements BillCreationController {
    @Autowired
    private ProfileController profileController;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private  PaymentRepository paymentRepository;
    @Autowired
    private DataBaseService dataBaseService;

    @Override
    public Account getProfile() {
        return profileController.getProfile();
    }

    @Override
    public Resident getResident() {
        return profileController.getResident();
    }


    @Transactional
    @Override
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

    @Override
    public void reset() {
        JavaFxApplication.showBilCreationScene();
    }
}
