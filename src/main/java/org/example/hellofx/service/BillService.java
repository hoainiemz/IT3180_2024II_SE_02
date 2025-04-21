package org.example.hellofx.service;

import org.example.hellofx.model.Bill;
import org.example.hellofx.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    public Bill findBillByBillId(Integer billId) {
        return billRepository.findBillByBillId(billId).get();
    }
}
