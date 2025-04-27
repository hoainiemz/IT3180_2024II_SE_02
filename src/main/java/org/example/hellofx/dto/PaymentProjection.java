package org.example.hellofx.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentProjection {
    Integer getBillId();
    LocalDateTime getDueDate();
    Boolean getRequired();
    String getContent();
    BigDecimal getAmount();
    LocalDateTime getPayTime();
    String getApartmentName(); // ✅ thêm tên căn hộ
}
