package org.example.hellofx.controller;

import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.controller.BillController;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillController{
    @Autowired
    private ProfileController profileController;

    public Resident getResident() {
        return profileController.getResident();
    }

    public Account getProfile() {
        return profileController.getProfile();
    }


}
