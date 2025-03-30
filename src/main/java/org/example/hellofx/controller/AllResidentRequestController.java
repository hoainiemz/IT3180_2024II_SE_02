package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;

public interface AllResidentRequestController {
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
     * the newresident is accepted and be a resident of bluemoon
     * @param newResident
     */
    void acceptButtonClicked(Resident newResident);
}
