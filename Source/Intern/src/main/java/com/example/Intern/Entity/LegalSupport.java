package com.example.Intern.Entity;

import com.example.Intern.Utility.Enum.STATUS_LEGAL;
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
@Table(name = TableName.LEGAL_SUPPORT)
public class LegalSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.LegalSupport.ID)
    private Long id;

    @ManyToOne
    @JoinColumn(name = ColumnName.LegalSupport.USER_ID, nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = ColumnName.LegalSupport.BUYER_ID)
    private User buyer;

    @ManyToOne
    @JoinColumn(name = ColumnName.LegalSupport.PROPERTY_ID, nullable = false)
    private Property property;

    @Column(name = ColumnName.LegalSupport.SERVICE_TYPE, nullable = false)
    private String serviceType;

    @Column(name = ColumnName.LegalSupport.STATUS, nullable = false)
    private STATUS_LEGAL status;

    @Column(name = ColumnName.LegalSupport.CREATE_AT, updatable = false)
    private Timestamp createAt;

    @Column(name = ColumnName.LegalSupport.UPDATE_AT)
    private Timestamp updateAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = new Timestamp(System.currentTimeMillis());
    }
}
