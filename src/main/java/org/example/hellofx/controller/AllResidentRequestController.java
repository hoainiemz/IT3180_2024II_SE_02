package org.example.hellofx.controller;


import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.controller.AllResidentRequestController;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllResidentRequestController{
    @Autowired
    private ProfileController profileController;
    @Autowired
    ResidentRepository residentRepository;

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }

    public void acceptButtonClicked(Resident newResident) {
        residentRepository.save(newResident);
    }
}
