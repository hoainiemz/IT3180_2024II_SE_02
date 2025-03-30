package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Resident;

import java.util.List;

public interface BillCreationController {
    /**
     * get current profile
     * @return current profile
     */
    Account getProfile();

    /**
     * get current resident
     * @return current resident
     */
    Resident getResident();

    /**
     *  create the bill
     */
    public void createButtonClicked(Bill bill, List<Integer> residentIds);

    /**
     * reset
     */
    public void reset();
}
