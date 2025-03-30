package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;

public interface BillManagerController {
    public Resident getResident();

    public Account getProfile();

    public void seeBillInformation(Integer billId);
}
