package org.example.hellofx.repository;

import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.GenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Integer> {
//    Optional<Resident> findByUserId(Integer userId);
    Optional<Resident> findByUserId(int userId);
    List<Resident> findAll();
    boolean existsByIdentityCard(String identityCard);

    @Modifying
    @Transactional
    @Query("UPDATE Resident SET firstName = :firstName, lastName = :lastName, dateOfBirth = :dateOfBirth, gender = :gender, identityCard = :identityCard, moveInDate = :moveInDate WHERE userId = :userId")
    int updateRowByUserId(
            @Param("userId") Integer userId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("dateOfBirth") LocalDate dateOfBirth,
            @Param("gender")GenderType gender,
            @Param("identityCard") String identityCard,
            @Param("moveInDate") LocalDate moveInDate
    );



    @Query(value = """
        SELECT DISTINCT ON (r.resident_id) r.*
        FROM resident r
        LEFT JOIN account acc ON r.user_id = acc.user_id
        LEFT JOIN settlement s ON r.resident_id = s.resident_id
        LEFT JOIN apartment a ON s.apartment_id = a.apartment_id
        WHERE (:houseNameFilter IS NULL OR :houseNameFilter = '' OR a.apartment_name = :houseNameFilter)
          AND (:roleFilter IS NULL OR :roleFilter = '' OR acc.role = :roleFilter)
          AND (
            :searchFilter IS NULL OR
            r.first_name ILIKE CONCAT('%', :searchFilter, '%') OR
            r.last_name ILIKE CONCAT('%', :searchFilter, '%')
          )
        ORDER BY r.resident_id, a.apartment_name NULLS LAST
    """, nativeQuery = true)
    List<Resident> findResidentsByFilters(
            @Param("houseNameFilter") String houseNameFilter,
            @Param("roleFilter") String roleFilter,
            @Param("searchFilter") String searchFilter
    );

    void deleteByResidentId(Integer residentId);
}
