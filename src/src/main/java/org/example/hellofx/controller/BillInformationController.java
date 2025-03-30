package org.example.hellofx.controller;

import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Resident;

import java.util.List;

public interface BillInformationController {
    public Resident getResident();

    public Account getProfile();

    public void saveButtonClicked(Bill oldBill, Bill bill, List<Integer> dsIn, List<Integer> dsOut);

    public void reset(Bill bill);
}
