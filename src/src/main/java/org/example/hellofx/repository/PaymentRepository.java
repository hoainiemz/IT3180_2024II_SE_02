package org.example.hellofx.repository;

import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

//    @Query("SELECT r, b, p.payTime FROM Resident r " +
//            "JOIN Payment p ON r.residentId = p.residentId " +
//            "JOIN Bill b ON p.billId = b.billId " +
//            "WHERE r.residentId = :residentId")
//    List<Object[]> findResidentBillsWithPayments(@Param("residentId") Integer residentId);
    @Query("SELECT r.residentId, r.userId, r.firstName, r.lastName, r.gender, r.dateOfBirth, " +
            "r.identityCard, r.houseId, r.moveInDate, " +
            "b.billId, b.amount, b.lateFee, b.dueDate, b.content, b.description, b.required, " +
            "p.payTime " +
            "FROM Resident r " +
            "JOIN Payment p ON r.residentId = p.residentId " +
            "JOIN Bill b ON p.billId = b.billId " +
            "WHERE r.residentId = :residentId")

    List<Object[]> findResidentBillsWithPayments(@Param("residentId") Integer residentId);

@Query("SELECT new org.example.hellofx.dto.ResidentBillPaymentDTO(" +
        "r.residentId, r.userId, r.firstName, r.lastName, r.gender, r.dateOfBirth, " +
        "r.identityCard, r.houseId, r.moveInDate, " +
        "b.billId, b.amount, b.lateFee, b.dueDate, b.content, b.description, b.required, " +
        "p.payTime) " +
        "FROM Resident r " +
        "JOIN Payment p ON r.residentId = p.residentId " +
        "JOIN Bill b ON p.billId = b.billId " +
        "WHERE r.residentId = :residentId " +
        "AND (:stateFilter = 0 OR (:stateFilter > 0 AND p.payTime IS NOT NULL) OR (:stateFilter < 0 AND p.payTime IS NULL)) " +
        "AND (:requireFilter = 0 OR (:requireFilter > 0 AND b.required = true) OR (:requireFilter < 0 AND b.required = false)) " +
        "AND (:dueFilter = 0 OR (:dueFilter > 0 AND b.dueDate > CURRENT_TIMESTAMP) OR (:dueFilter < 0 AND b.dueDate < CURRENT_TIMESTAMP)) " +
        "AND (:searchFilter IS NULL OR b.content LIKE %:searchFilter%)")
List<ResidentBillPaymentDTO> findResidentBillsWithResidentFilters(
        @Param("residentId") Integer residentId,
        @Param("stateFilter") int stateFilter,
        @Param("requireFilter") int requireFilter,
        @Param("dueFilter") int dueFilter,
        @Param("searchFilter") String searchFilter);


    @Query("SELECT new org.example.hellofx.dto.ResidentBillPaymentDTO(" +
            "r.residentId, r.userId, r.firstName, r.lastName, r.gender, r.dateOfBirth, " +
            "r.identityCard, r.houseId, r.moveInDate, " +
            "b.billId, b.amount, b.lateFee, b.dueDate, b.content, b.description, b.required, " +
            "p.payTime) " +
            "FROM Resident r " +
            "JOIN Payment p ON r.residentId = p.residentId " +
            "JOIN Bill b ON p.billId = b.billId " +
            "WHERE b.billId = :billId " +
            "AND (:stateFilter = 0 OR (:stateFilter > 0 AND p.payTime IS NOT NULL) OR (:stateFilter < 0 AND p.payTime IS NULL)) " +
            "AND (:requireFilter = 0 OR (:requireFilter > 0 AND b.required = true) OR (:requireFilter < 0 AND b.required = false)) " +
            "AND (:dueFilter = 0 OR (:dueFilter > 0 AND b.dueDate > CURRENT_TIMESTAMP) OR (:dueFilter < 0 AND b.dueDate < CURRENT_TIMESTAMP)) " +
            "AND (:searchFilter IS NULL OR b.content LIKE %:searchFilter%)")
    List<ResidentBillPaymentDTO> findResidentBillsWithBillFilters(
            @Param("billId") Integer billId,
            @Param("stateFilter") int stateFilter,
            @Param("requireFilter") int requireFilter,
            @Param("dueFilter") int dueFilter,
            @Param("searchFilter") String searchFilter);


    public List<Payment> findPaymentByBillId(Integer billId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.paymentId IN :ids")
    void deletePaymentsByPaymentId(@Param("ids") List<Integer> ids);
}
