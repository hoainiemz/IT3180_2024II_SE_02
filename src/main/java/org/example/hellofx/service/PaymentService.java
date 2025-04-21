package org.example.hellofx.service;

import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.enums.GenderType;
import org.example.hellofx.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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


    public List<ResidentBillPaymentDTO> getResidentPayments(Integer residentId) {
        List<Object[]> results = paymentRepository.findResidentBillsWithPayments(residentId);

        return results.stream().map(row -> new ResidentBillPaymentDTO(
                (Integer) row[0],  // residentId
                (Integer) row[1],  // userId
                (String) row[2],   // firstName
                (String) row[3],   // lastName
                (GenderType) row[4],   // gender (should be GenderType if applicable)
                (LocalDate) row[5], // dateOfBirth
                (String) row[6],   // identityCard
                (String) row[7],   // houseId
                (LocalDate) row[8], // moveInDate
                (Integer) row[9],  // billId
                (Double) row[10],  // amount
                (Double) row[11],  // lateFee
                (LocalDateTime) row[12], // dueDate
                (String) row[13],  // content
                (String) row[14],  // description
                (Boolean) row[15], // required
                (LocalDateTime) row[16] // payTime
        )).collect(Collectors.toList());
    }

    @Transactional
    public void saveAllPayments(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }


    @Transactional
    public void deletePayments(List<Integer> dsOut) {
        if (dsOut == null || dsOut.isEmpty()) {
            throw new IllegalArgumentException("List of bill IDs cannot be null or empty");
        }
        paymentRepository.deletePaymentsByPaymentId(dsOut);
    }
}
