package org.example.hellofx.service;

import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.model.Payment;
import org.example.hellofx.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public List<Payment> findPaymentByBillId(Integer billId) {
        return paymentRepository.findPaymentByBillId(billId);
    }

    public List<ResidentBillPaymentDTO> findPaymentByResidentFilters(Integer residentId, int stateFilter, int requireFilter, int dueFilter, String searchFilter) {
        return paymentRepository.findResidentBillsWithResidentFilters(residentId, stateFilter, requireFilter, dueFilter, searchFilter);
    }
}
