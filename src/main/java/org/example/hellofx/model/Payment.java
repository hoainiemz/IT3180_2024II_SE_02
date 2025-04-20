    package org.example.hellofx.model;

    import jakarta.persistence.*;

    import java.time.LocalDateTime;

    @Entity
    @Table(name = "payment")
    public class Payment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "payment_id")
        private Integer paymentId;

        @Column(name = "bill_id")
        private Integer billId;

        @Column(name = "resident_id")
        private Integer residentId;

        @Column(name = "pay_time")
        private LocalDateTime payTime;

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public void setBillId(Integer billId) {
            this.billId = billId;
        }

        public void setResidentId(Integer residentId) {
            this.residentId = residentId;
        }

        public void setPayTime(LocalDateTime payTime) {
            this.payTime = payTime;
        }

        public Payment() {
        }

        public Payment(Integer paymentId, Integer billId, Integer residentId, LocalDateTime payTime) {
            this.paymentId = paymentId;
            this.billId = billId;
            this.residentId = residentId;
            this.payTime = payTime;
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public Integer getBillId() {
            return billId;
        }

        public Integer getResidentId() {
            return residentId;
        }

        public LocalDateTime getPayTime() {
            return payTime;
        }
    }
