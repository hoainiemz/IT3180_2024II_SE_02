package org.example.hellofx.service;

import org.example.hellofx.model.Vehicle;
import org.example.hellofx.model.enums.VehicleType;
import org.example.hellofx.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    public List<Vehicle> getVehiclesByFilters(String houseIdFilter, VehicleType typeFilter, String searchFilter) {
        return vehicleRepository.findVehiclesByFilters(houseIdFilter, typeFilter, searchFilter);
    }

    public List<Vehicle> getByResidentAndFilters(Integer residentId, String houseIdFilter, VehicleType typeFilter, String searchFilter) {
        return vehicleRepository.findByResidentAndFilters(residentId, houseIdFilter, typeFilter, searchFilter);
    }
}
