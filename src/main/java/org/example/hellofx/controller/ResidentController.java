package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.service.AccountService;
import org.example.hellofx.service.ResidentService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResidentController {
    @Autowired
    private ProfileController profileController;

    @Autowired
    private ResidentService residentService;
    @Autowired
    private AccountService accountService;

    public List<Resident> getResidentList() {
        return residentService.residentsQuery(profileController.getProfile(), profileController.getResident());
    }

    public void seeMoreInformation(int id) {
        Account profile = accountService.findAccountByUserId(id);
        Resident resident = residentService.findResidentByUserId(id);
        JavaFxApplication.showUserInformationScene(profile, resident);
    }

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }

    public ObservableList<Resident> residentQuery(String query) {
        return FXCollections.observableArrayList(residentService.nativeResidentQuery(query));
    }

    public ObservableList<String> getAllHouseIds(){
        return FXCollections.observableArrayList(residentService.findDistinctNonNullHouseId(getProfile(), getResident()));
    }
}
