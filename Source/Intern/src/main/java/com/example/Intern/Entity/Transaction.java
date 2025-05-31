package com.example.Intern.Entity;

import com.example.Intern.Utility.Enum.STATUS_TRANSACTION;
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
@Table(name = TableName.TRANSACTIONS)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.Transactions.ID)
    private Long id;

    @ManyToOne
    @JoinColumn(name = ColumnName.Transactions.BUYER_ID, nullable = false)
    private User buyer;

    @ManyToOne
    @JoinColumn(name = ColumnName.Transactions.PROPERTY_ID, nullable = false)
    private Property property;

    @Column(name = ColumnName.Transactions.TRANSACTION_DATE, nullable = false)
    private Timestamp transactionDate;

    @Column(name = ColumnName.Transactions.TRANSACTION_AMOUNT, nullable = false)
    private Double transactionAmount;

    @Column(name = ColumnName.Transactions.STATUS, nullable = false)
    private STATUS_TRANSACTION status;

    @PrePersist
    protected void onCreate() {
        this.transactionDate = new Timestamp(System.currentTimeMillis());
    }
}
