package org.example.hellofx.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.repository.ResidentRepository;
import org.example.hellofx.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllResidentRequestController{
    @Autowired
    private ProfileController profileController;
    @Autowired
    ResidentRepository residentRepository;
    @Autowired
    private AccountService accountService;

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }

    public void acceptButtonClicked(Resident newResident) {
        residentRepository.save(newResident);
    }

    public ObservableList<Account> accountsQuery(String query) {
        return FXCollections.observableArrayList(accountService.nativeAccountQuery(query));
    }
}
