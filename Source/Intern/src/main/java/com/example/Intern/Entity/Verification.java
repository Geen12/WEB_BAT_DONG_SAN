package com.example.Intern.Entity;

import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
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
@Table(name = TableName.VERIFICATIONS)
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.Verifications.ID)
    private Long id;

    @ManyToOne
    @JoinColumn(name = ColumnName.Verifications.PROPERTY_ID, nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = ColumnName.Verifications.VERIFIER_ID, nullable = true)
    private User verifier;

    @Column(name = ColumnName.Verifications.NOTES)
    private String notes;

    @Column(name = ColumnName.Verifications.VERIFICATION_DATE)
    private Timestamp verificationDate;

    @PrePersist
    protected void onCreate() {
        this.verificationDate = new Timestamp(System.currentTimeMillis());
    }
}