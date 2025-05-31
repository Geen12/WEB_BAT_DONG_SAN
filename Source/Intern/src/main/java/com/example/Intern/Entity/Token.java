package com.example.Intern.Entity;

import com.example.Intern.Utility.constants.ColumnName;
import com.example.Intern.Utility.constants.TableName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = TableName.TOKEN)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.Token.ID)
    private Long id;

    @Column(name = ColumnName.Token.TOKEN, nullable = false)
    private String token;

    @Column(name = ColumnName.Token.CREATE_AT, nullable = false)
    private Timestamp create_at;

    @OneToOne
    @JoinColumn(name = ColumnName.Token.USER_ID, referencedColumnName = ColumnName.Users.ID, unique = true, nullable = false)
    private User user;

    @PrePersist
    protected void createAt() {
        this.create_at = new Timestamp(System.currentTimeMillis());
    }
}
