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
    List<Resident> findByHouseId(String houseId);

    @Query("SELECT DISTINCT u.houseId FROM Resident u WHERE u.houseId IS NOT NULL")
    List<String> findDistinctNonNullHouseId();
    boolean existsByIdentityCard(String identityCard);

    @Modifying
    @Transactional
    @Query("UPDATE Resident SET firstName = :firstName, lastName = :lastName, dateOfBirth = :dateOfBirth, gender = :gender, houseId = :houseId, identityCard = :identityCard, moveInDate = :moveInDate WHERE userId = :userId")
    int updateRowByUserId(
            @Param("userId") Integer userId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("dateOfBirth") LocalDate dateOfBirth,
            @Param("gender")GenderType gender,
            @Param("houseId") String houseId,
            @Param("identityCard") String identityCard,
            @Param("moveInDate") LocalDate moveInDate
    );
}
