package org.example.hellofx.service;

import org.example.hellofx.dto.VehicleInfo;
import org.example.hellofx.model.Vehicle;
import org.example.hellofx.model.enums.VehicleType;
import org.example.hellofx.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    public List<VehicleInfo> getVehicleInfoByFilters(String houseIdFilter, VehicleType typeFilter, String searchFilter) {
        for (int i = 0; i < 100; i++) {
            try {
                return vehicleRepository.findVehicleInfoByFilters(houseIdFilter, typeFilter, searchFilter);
            }
            catch (Exception e) {
                continue;
            }
        }
        return vehicleRepository.findVehicleInfoByFilters(houseIdFilter, typeFilter, searchFilter);
    }

    public List<VehicleInfo> getVehicleInfoByResidentAndFilters(Integer residentId, String houseIdFilter, VehicleType typeFilter, String searchFilter) {
        for (int i = 0; i < 100; i++) {
            try {
                return vehicleRepository.findVehicleInfoByResidentAndFilters(residentId, houseIdFilter, typeFilter, searchFilter);
            }
            catch (Exception e) {
                continue;
            }
        }
        return vehicleRepository.findVehicleInfoByResidentAndFilters(residentId, houseIdFilter, typeFilter, searchFilter);
    }

    public Vehicle save(Vehicle vehicle) {
        for (int i = 0; i < 100; i++) {
            try {
                return vehicleRepository.save(vehicle);
            }
            catch (Exception e) {
                continue;
            }
        }
        return vehicleRepository.save(vehicle);
    }

    public boolean checkExistByLicensePlate(String val) {
        return vehicleRepository.existsByLicensePlate(val);
    }

    public void deleteVehicleById(Integer id) {
        vehicleRepository.deleteByVehicleId(id);
    }
}
