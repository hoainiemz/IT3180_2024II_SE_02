package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.dto.ApartmentCountProjection;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.repository.SettlementRepository;
import org.example.hellofx.service.SettlementService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApartmentController {
    @Autowired
    private ProfileController profileController;
    @Autowired
    private SettlementService settlementService;

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }

    public ObservableList<ApartmentCountProjection> getApartmentsAndResidentCount(String s) {
        if (getProfile().getRole() == AccountType.Resident) {
            return FXCollections.observableArrayList(settlementService.getApartmentsAndResidentCount(getResident().getResidentId(), s));
        }
        return FXCollections.observableArrayList(settlementService.getApartmentsAndResidentCount(s));
    }

    public void seeMoreInformation(Integer apartmentId) {
        JavaFxApplication.showApartmentInformationScene(apartmentId);
    }
}
