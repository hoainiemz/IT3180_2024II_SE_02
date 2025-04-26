package org.example.hellofx.repository;

import org.example.hellofx.model.Vehicle;
import org.example.hellofx.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    // Tìm tất cả xe theo apartmentId
    List<Vehicle> findByApartmentId(Integer apartmentId);

    // Tìm xe theo biển số (license plate)
    Vehicle findByLicensePlate(String licensePlate);

    // Xóa xe theo biển số
    void deleteByLicensePlate(String licensePlate);


    @Query("""
        SELECT v
        FROM Vehicle v
        JOIN Apartment a ON v.apartmentId = a.apartmentId
        WHERE 
          (:houseIdFilter IS NULL    OR :houseIdFilter = '' OR a.apartmentName = :houseIdFilter)
          AND (:typeFilter    IS NULL OR v.vehicleType = :typeFilter)
          AND (:searchFilter  IS NULL OR :searchFilter = '' OR v.licensePlate LIKE CONCAT('%', :searchFilter, '%'))
    """)
    List<Vehicle> findVehiclesByFilters(
            @Param("houseIdFilter") String houseIdFilter,
            @Param("typeFilter")    VehicleType typeFilter,
            @Param("searchFilter")  String searchFilter
    );


    @Query("""
        SELECT distinct v
        FROM Vehicle v
        JOIN Apartment a   ON v.apartmentId = a.apartmentId
        JOIN Settlement s ON s.apartmentId = v.apartmentId
        WHERE 
          s.residentId = :residentId
          AND (:houseIdFilter IS NULL OR :houseIdFilter = '' OR a.apartmentName = :houseIdFilter)
          AND (:typeFilter     IS NULL OR v.vehicleType = :typeFilter)
          AND (:searchFilter   IS NULL OR :searchFilter = '' OR v.licensePlate LIKE CONCAT('%', :searchFilter, '%'))
    """)
    List<Vehicle> findByResidentAndFilters(
            @Param("residentId")    Integer      residentId,
            @Param("houseIdFilter") String       houseIdFilter,
            @Param("typeFilter")    VehicleType  typeFilter,
            @Param("searchFilter")  String       searchFilter
    );
}