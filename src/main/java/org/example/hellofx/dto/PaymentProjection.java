package org.example.hellofx.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

public interface PaymentProjection {
    LocalDateTime getDueDate();
    Boolean getRequired();
    String getContent();
    BigDecimal getAmount();
    LocalDateTime getPayTime();
    String getApartmentName(); // ✅ thêm tên căn hộ
}
