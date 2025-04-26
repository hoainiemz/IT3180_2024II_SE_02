package org.example.hellofx.dto;

import org.example.hellofx.model.enums.VehicleType;
import java.time.LocalDateTime;

public interface VehicleInfo {
    Integer       getVehicleId();
    String        getLicensePlate();
    VehicleType   getVehicleType();
    LocalDateTime getRegistrationDate();
    String        getApartmentName();
}
