package org.example.hellofx.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.model.enums.GenderType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositoryImpl<T>{
    @PersistenceContext
    private EntityManager entityManager;

    public List<T> executeRawSql(String sqlQuery, Class<T> type) {
        return entityManager.createNativeQuery(sqlQuery, type).getResultList();
    }

    public List<ResidentBillPaymentDTO> executeRawSql2(String query, Class<ResidentBillPaymentDTO> clazz) {
        Query nativeQuery = entityManager.createNativeQuery(query);
        List<Object[]> results = nativeQuery.getResultList();

        return results.stream().map(row -> new ResidentBillPaymentDTO(
                (Integer) row[0],  // residentId
                (Integer) row[1],  // userId
                (String) row[2],   // firstName
                (String) row[3],   // lastName
                row[4] != null ? GenderType.valueOf((String) row[4]) : null, // ✅ Convert String to GenderType
                row[5] != null ? ((java.sql.Date) row[5]).toLocalDate() : null, // Convert SQL Date to LocalDate
                (String) row[6],   // identityCard
                (String) row[7],   // houseId
                row[8] != null ? ((java.sql.Date) row[8]).toLocalDate() : null, // Convert SQL Date to LocalDate
                (Integer) row[9],  // billId
                (Double) row[10],  // amount
                (Double) row[11],  // lateFee
                (LocalDateTime) row[12], // dueDate
                (String) row[13],  // content
                (String) row[14],  // description
                (Boolean) row[15], // required
                (LocalDateTime) row[16] // payTime
        )).collect(Collectors.toList());
    }

}
