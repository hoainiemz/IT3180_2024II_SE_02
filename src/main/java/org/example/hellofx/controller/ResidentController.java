package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }

    public ObservableList<Resident> residentQuery(String query) {
        return FXCollections.observableArrayList(dataBaseService.nativeResidentQuery(query));
    }

    public ObservableList<String> getAllHouseIds(){
        return FXCollections.observableArrayList(dataBaseService.findDistinctNonNullHouseId(getProfile(), getResident()));
    }
}
