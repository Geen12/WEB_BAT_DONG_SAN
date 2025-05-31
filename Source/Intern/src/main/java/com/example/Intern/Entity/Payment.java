package com.example.Intern.Entity;

import com.example.Intern.Utility.Enum.STATUS_PAYMENT;
import com.example.Intern.Utility.constants.ColumnName;
import com.example.Intern.Utility.constants.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = TableName.PAYMENTS)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.Payments.ID)
    private Long id;

    @ManyToOne
    @JoinColumn(name = ColumnName.Payments.USER_ID, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = ColumnName.Payments.TRANSACTION_ID, nullable = false)
    private Transaction transaction;

    @Column(name = ColumnName.Payments.PAYMENT_METHOD, nullable = false)
    private String paymentMethod;

    @Column(name = ColumnName.Payments.PAYMENT_AMOUNT, nullable = false)
    private Double paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnName.Payments.PAYMENT_STATUS, nullable = false)
    private STATUS_PAYMENT paymentStatus;

    @Column(name = ColumnName.Payments.PAYMENT_DATE)
    private Timestamp paymentDate;

    @PrePersist
    protected void onCreate() {
        this.paymentDate = new Timestamp(System.currentTimeMillis());
    }
}
