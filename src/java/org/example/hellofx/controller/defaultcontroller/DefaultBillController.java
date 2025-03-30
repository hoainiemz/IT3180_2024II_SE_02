package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.controller.BillController;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultBillController implements BillController {
    @Autowired
    private ProfileController profileController;
    @Override
    public Resident getResident() {
        return profileController.getResident();
    }

    @Override
    public Account getProfile() {
        return profileController.getProfile();
    }


}
