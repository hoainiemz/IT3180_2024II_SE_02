package org.example.hellofx.dto;

import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.GenderType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ResidentBillPaymentDTO {
//    private Resident resident;
//    private Bill bill;
    private LocalDateTime payTime;

    private Integer residentId;
    private Integer userId;
    private String firstName;
    private String lastName;
    private GenderType gender;
    private LocalDate dateOfBirth;
    private String identityCard;
    private String houseId;
    private LocalDate moveInDate;
    private Integer billId;
    private Double amount;
    private Double lateFee;
    private LocalDateTime dueDate;
    private String content;
    private String description;
    private Boolean required;

    // ✅ Constructor with 17 parameters
    public ResidentBillPaymentDTO(Integer residentId, Integer userId, String firstName, String lastName,
                                  GenderType gender, LocalDate dateOfBirth, String identityCard, String houseId,
                                  LocalDate moveInDate, Integer billId, Double amount, Double lateFee,
                                  LocalDateTime dueDate, String content, String description, Boolean required,
                                  LocalDateTime payTime) {
        this.residentId = residentId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.identityCard = identityCard;
        this.houseId = houseId;
        this.moveInDate = moveInDate;
        this.billId = billId;
        this.amount = amount;
        this.lateFee = lateFee;
        this.dueDate = dueDate;
        this.content = content;
        this.description = description;
        this.required = required;
        this.payTime = payTime;
    }

    // ✅ Constructor for JPQL Query
//    public ResidentBillPaymentDTO(Resident resident, Bill bill, LocalDateTime payTime) {
//        this.resident = resident;
//        this.bill = bill;
//        this.payTime = payTime;
//    }

//    public Resident getResident() {
//        return resident;
//    }
//
//    public Bill getBill() {
//        return bill;
//    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public Integer getResidentId() {
        return residentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GenderType getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public String getHouseId() {
        return houseId;
    }

    public LocalDate getMoveInDate() {
        return moveInDate;
    }

    public Integer getBillId() {
        return billId;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getLateFee() {
        return lateFee;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getRequired() {
        return required;
    }

    public Double getFee() {
        return amount;
    }
}
