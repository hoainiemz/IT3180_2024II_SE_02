package org.example.hellofx.repository;

import org.example.hellofx.dto.ApartmentCountProjection;
import org.example.hellofx.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    @Query(value = """
    SELECT a.apartment_id AS apartmentId,
           a.apartment_name AS apartmentName,
           COUNT(s2.resident_id) AS residentCount
    FROM apartment a
    JOIN settlement s1 ON a.apartment_id = s1.apartment_id
    LEFT JOIN settlement s2 ON a.apartment_id = s2.apartment_id
    WHERE s1.resident_id = :residentId
      AND CAST(a.apartment_id AS TEXT) ILIKE CONCAT('%', :search, '%')
    GROUP BY a.apartment_id, a.apartment_name
    """, nativeQuery = true)
    List<ApartmentCountProjection> findFilteredApartmentCountsByResidentId(
            @Param("residentId") Integer residentId,
            @Param("search") String search
    );


    @Query(value = """
    SELECT a.apartment_id AS apartmentId,
           a.apartment_name AS apartmentName,
           COUNT(s.resident_id) AS residentCount
    FROM apartment a
    LEFT JOIN settlement s ON a.apartment_id = s.apartment_id
    WHERE CAST(a.apartment_id AS TEXT) ILIKE CONCAT('%', :search, '%')
    GROUP BY a.apartment_id, a.apartment_name
    """, nativeQuery = true)
    List<ApartmentCountProjection> findApartmentCountsBySearch(
            @Param("search") String search
    );

    List<Settlement> findSettlementsByApartmentId(Integer apartmentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Settlement s WHERE s.settlementId IN :ids")
    void deleteSettlementsBySettlementId(@Param("ids") List<Integer> ids);

    List<Settlement> findSettlementsByResidentId(Integer residentId);

    @Query(value = """
        SELECT a.apartment_name
        FROM apartment a
        JOIN settlement s ON a.apartment_id = s.apartment_id
        WHERE s.resident_id = :residentId
    """, nativeQuery = true)
    List<String> findApartmentNamesByResidentId(@Param("residentId") Integer residentId);

    @Query(value = """
    SELECT DISTINCT a.apartment_name
    FROM apartment a
    LEFT JOIN settlement s ON a.apartment_id = s.apartment_id
""", nativeQuery = true)
    List<String> findAllApartmentNames();
}
