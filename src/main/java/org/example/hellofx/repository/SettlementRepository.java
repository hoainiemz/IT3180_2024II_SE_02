package org.example.hellofx.repository;

import org.example.hellofx.dto.ApartmentCountProjection;
import org.example.hellofx.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    @Query(value = """
    SELECT s.apartment_id AS apartmentId, COUNT(*) AS residentCount
    FROM settlement s
    WHERE s.apartment_id IN (
        SELECT apartment_id FROM settlement WHERE resident_id = :residentId
    )
    AND s.apartment_id ILIKE CONCAT('%', :search, '%')
    GROUP BY s.apartment_id
    """, nativeQuery = true)
    List<ApartmentCountProjection> findFilteredApartmentCountsByResidentId(
            @Param("residentId") Integer residentId,
            @Param("search") String search
    );

    @Query(value = """
    SELECT a.apartment_id AS apartmentId, COUNT(s.resident_id) AS residentCount
    FROM apartment a
    LEFT JOIN settlement s ON a.apartment_id = s.apartment_id
    WHERE a.apartment_id ILIKE CONCAT('%', :search, '%')
    GROUP BY a.apartment_id
    """, nativeQuery = true)
    List<ApartmentCountProjection> findApartmentCountsBySearch(@Param("search") String search);


}
