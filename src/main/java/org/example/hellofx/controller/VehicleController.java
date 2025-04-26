package org.example.hellofx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.Vehicle;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.model.enums.VehicleType;
import org.example.hellofx.service.ResidentService;
import org.example.hellofx.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleController {
    @Autowired
    private ProfileController profileController;
    @Autowired
    private ResidentService residentService;
    @Autowired
    private VehicleService vehicleService;

    public Account getProfile() {
        return profileController.getProfile();
    }

    public Resident getResident() {
        return profileController.getResident();
    }

    public ObservableList<String> getAllApartmentName() {
        return FXCollections.observableArrayList(residentService.findDistinctNonNullHouseId(getProfile(), getResident()));
    }

    public ObservableList<Vehicle> getVehiclesByFilter(String houseIdFilter, VehicleType typeFilter, String searchFilter) {
        if (getProfile().getRole() != AccountType.Resident) {
            return FXCollections.observableArrayList(vehicleService.getVehiclesByFilters(houseIdFilter, typeFilter, searchFilter));
        }
        return FXCollections.observableArrayList(vehicleService.getByResidentAndFilters(getResident().getResidentId(), houseIdFilter, typeFilter, searchFilter));
    }
}
