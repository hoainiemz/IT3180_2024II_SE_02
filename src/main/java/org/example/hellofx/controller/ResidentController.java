package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResidentController {
    @Autowired
    private DataBaseService dataBaseService;
    @Autowired
    private ProfileController profileController;

    public List<Resident> getResidentList() {
        return dataBaseService.residentsQuery(profileController.getProfile(), profileController.getResident());
    }

    public void seeMoreInformation(int id) {
        Account profile = dataBaseService.findAccountByUserId(id);
        Resident resident = dataBaseService.findResidentByUserId(id);
        JavaFxApplication.showUserInformationScene(profile, resident);
    }
}
