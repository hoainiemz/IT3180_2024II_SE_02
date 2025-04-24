package org.example.hellofx.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "apartment")
public class Apartment {

    @Id
    @Column(name = "apartment_id", nullable = false, unique = true)
    private String apartmentId;

    @Column(name = "monthly_rent_price", precision = 12, scale = 2)
    private BigDecimal monthlyRentPrice;

    @Column(name = "last_month_electric_index")
    private int lastMonthElectricIndex;

    @Column(name = "electric_unit_price", precision = 10, scale = 2)
    private BigDecimal electricUnitPrice;

    @Column(name = "last_month_water_index")
    private int lastMonthWaterIndex;

    @Column(name = "water_unit_price", precision = 10, scale = 2)
    private BigDecimal waterUnitPrice;

    // Constructors
    public Apartment() {}

    public Apartment(String apartmentId, BigDecimal monthlyRentPrice, int lastMonthElectricIndex,
                     BigDecimal electricUnitPrice, int lastMonthWaterIndex, BigDecimal waterUnitPrice) {
        this.apartmentId = apartmentId;
        this.monthlyRentPrice = monthlyRentPrice;
        this.lastMonthElectricIndex = lastMonthElectricIndex;
        this.electricUnitPrice = electricUnitPrice;
        this.lastMonthWaterIndex = lastMonthWaterIndex;
        this.waterUnitPrice = waterUnitPrice;
    }

    // Getters and Setters
    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public BigDecimal getMonthlyRentPrice() {
        return monthlyRentPrice;
    }

    public void setMonthlyRentPrice(BigDecimal monthlyRentPrice) {
        this.monthlyRentPrice = monthlyRentPrice;
    }

    public int getLastMonthElectricIndex() {
        return lastMonthElectricIndex;
    }

    public void setLastMonthElectricIndex(int lastMonthElectricIndex) {
        this.lastMonthElectricIndex = lastMonthElectricIndex;
    }

    public BigDecimal getElectricUnitPrice() {
        return electricUnitPrice;
    }

    public void setElectricUnitPrice(BigDecimal electricUnitPrice) {
        this.electricUnitPrice = electricUnitPrice;
    }

    public int getLastMonthWaterIndex() {
        return lastMonthWaterIndex;
    }

    public void setLastMonthWaterIndex(int lastMonthWaterIndex) {
        this.lastMonthWaterIndex = lastMonthWaterIndex;
    }

    public BigDecimal getWaterUnitPrice() {
        return waterUnitPrice;
    }

    public void setWaterUnitPrice(BigDecimal waterUnitPrice) {
        this.waterUnitPrice = waterUnitPrice;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentId='" + apartmentId + '\'' +
                ", monthlyRentPrice=" + monthlyRentPrice +
                ", lastMonthElectricIndex=" + lastMonthElectricIndex +
                ", electricUnitPrice=" + electricUnitPrice +
                ", lastMonthWaterIndex=" + lastMonthWaterIndex +
                ", waterUnitPrice=" + waterUnitPrice +
                '}';
    }
}
