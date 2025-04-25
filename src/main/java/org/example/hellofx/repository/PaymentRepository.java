package org.example.hellofx.repository;

import org.example.hellofx.dto.PaymentProjection;
import org.example.hellofx.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {


    @Query("""
    SELECT 
        p.bill.dueDate AS dueDate,
        p.bill.required AS required,
        p.bill.content AS content,
        p.bill.amount AS amount,
        p.payTime AS payTime,
        p.apartment.apartmentName AS apartmentName
    FROM Payment p
    JOIN Settlement s ON s.apartmentId = p.apartment.apartmentId
    WHERE s.residentId = :residentId
      AND (
        :stateFilter = 0 OR 
        (:stateFilter = 1 AND p.bill.dueDate < CURRENT_TIMESTAMP) OR 
        (:stateFilter = -1 AND p.bill.dueDate >= CURRENT_TIMESTAMP)
      )
      AND (
        :requireFilter = 0 OR 
        (:requireFilter = 1 AND p.bill.required = true) OR 
        (:requireFilter = -1 AND p.bill.required = false)
      )
      AND (
        :searchFilter IS NULL OR 
        LOWER(p.bill.content) LIKE LOWER(CONCAT('%', :searchFilter, '%')) OR 
        LOWER(p.bill.description) LIKE LOWER(CONCAT('%', :searchFilter, '%'))
      )
    """)
    List<PaymentProjection> findPaymentsByResidentAndFilters(
            @Param("residentId") Integer residentId,
            @Param("stateFilter") int stateFilter,
            @Param("requireFilter") int requireFilter,
            @Param("searchFilter") String searchFilter
    );


    @Query("SELECT p FROM Payment p WHERE p.bill.billId = :billId")
    List<Payment> findPaymentByBillId(@Param("billId") Integer billId);



    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.paymentId IN :ids")
    void deletePaymentsByPaymentId(@Param("ids") List<Integer> ids);
}
