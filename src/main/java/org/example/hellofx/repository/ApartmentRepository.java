package org.example.hellofx.repository;

import org.example.hellofx.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, String> {

    boolean existsByApartmentId(String apartmentId);

}
