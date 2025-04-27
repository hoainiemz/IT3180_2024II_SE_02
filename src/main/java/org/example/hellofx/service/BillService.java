package org.example.hellofx.service;

import org.example.hellofx.model.Bill;
import org.example.hellofx.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BillService {
    @Autowired
    BillRepository billRepository;

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    public Bill findBillByBillId(Integer billId) {
        return billRepository.findBillByBillId(billId).get();
    }
    
    @Transactional(readOnly = true)
    public List<Bill> findBillsByFilters(int requireFilter, int dueFilter, String searchFilter) {
        for (int retry = 0; retry < 3; retry++) {
            try {
                return billRepository.findBillsWithFilters(requireFilter, dueFilter, searchFilter);
            } catch (Exception e) {
                if (retry == 2) throw e;
            }
        }
        return List.of(); // Fallback empty list if all retries fail
    }


    @Transactional
    public void updateBill(Bill bill) {
        int d = 10;
        while (d --> 0) {
            try {
                billRepository.save(bill);
                return;
            } catch (Exception e) {
                continue;
            }
        }
    }

    public void deleteBillById(Integer id) {
        billRepository.deleteByBillId(id);
    }
}
