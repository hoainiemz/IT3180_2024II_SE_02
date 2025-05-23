package org.example.hellofx.service;

import org.example.hellofx.dto.PaymentProjection;
import org.example.hellofx.model.Apartment;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.enums.GenderType;
import org.example.hellofx.repository.ApartmentRepository;
import org.example.hellofx.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ApartmentRepository apartmentRepository;

    public List<Payment> findPaymentByBillId(Integer billId) {
        return paymentRepository.findPaymentByBillId(billId);
    }


    @Transactional
    public void saveAllPayments(List<Payment> payments) {
        for (int i = 0; i < 10; i++) {
            try {
                paymentRepository.saveAll(payments);
            }
            catch (Exception e) {
                continue;
            }
        }
    }


    @Transactional
    public void deletePayments(List<Integer> dsOut) {
        if (dsOut == null || dsOut.isEmpty()) {
            throw new IllegalArgumentException("List of bill IDs cannot be null or empty");
        }
        paymentRepository.deletePaymentsByPaymentId(dsOut);
    }

    public List<PaymentProjection> getPaymentProjectionByResidentIdAndFilters(Integer residentId, int stateFilter, int requireFilter, int dueFilter, String searchFilter) {
        return paymentRepository.findPaymentsByResidentAndFilters(residentId, stateFilter, requireFilter, dueFilter, searchFilter);
    }


    @Transactional
    public void generatePaymentsForBill(Bill bill) {
        List<Apartment> occupied = apartmentRepository.findOccupiedApartments();

        List<Payment> payments = occupied.stream().map(ap -> {
            Payment p = new Payment();
            p.setBill(bill);               // liên kết đến Bill
            p.setApartment(ap);            // liên kết đến Apartment
            p.setPayAmount(BigDecimal.ZERO);
            p.setPayTime(null);
            return p;
        }).collect(Collectors.toList());

        paymentRepository.saveAll(payments);
    }
}
