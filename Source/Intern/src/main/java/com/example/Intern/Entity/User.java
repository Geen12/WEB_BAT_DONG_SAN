package com.example.Intern.Entity;

import com.example.Intern.Utility.Enum.GENDER;
import com.example.Intern.Utility.Enum.ROLE;
import com.example.Intern.Utility.Enum.STATUS_USER;
import com.example.Intern.Utility.constants.ColumnName;
import com.example.Intern.Utility.constants.TableName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@DynamicUpdate
@Entity
@Table(name = TableName.USERS)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.Users.ID)
    private Long id;

    @NotBlank(message = "Username maybe not blank")
    @Column(name = ColumnName.Users.USER_NAME, nullable = false, unique = true)
    private String user_name;

    @NotBlank(message = "Password maybe not blank")
    @Column(name = ColumnName.Users.PASSWORD, nullable = false)
    private String password;

    @NotBlank(message = "Full name maybe not blank")
    @Column(name = ColumnName.Users.FULL_NAME, nullable = false)
    private String full_name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email maybe not blank")
    @Column(name = ColumnName.Users.EMAIL, nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number maybe not blank")
    @Size(min=10, max = 15, message = "Phone number must be between 10 and 15 digits")
    @Column(name = ColumnName.Users.PHONE_NUMBER, nullable = false)
    private String phone_number;

    @NotBlank(message = "Address maybe not blank")
    @Column(name = ColumnName.Users.ADDRESS, nullable = false)
    private String address;

    @Column(name = ColumnName.Users.DATE_OF_BIRTH, nullable = false)
    private LocalDate date_of_birth;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnName.Users.GENDER, nullable = false)
    private GENDER gender;

    @Column(name = ColumnName.Users.ACCEPT_TERMS, nullable = false)
    private Boolean accept_terms;

    @Column(name = ColumnName.Users.CREATE_AT, updatable = false)
    protected Timestamp create_at;

    @Column(name = ColumnName.Users.UPDATE_AT)
    protected Timestamp update_at;

    @Column(name = ColumnName.Users.STATUS)
    private STATUS_USER statusUser;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Role role;

    @Column(name = ColumnName.Users.ROLE)
    private ROLE role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Token token;

    @PrePersist
    protected void onCreate() {
        this.create_at = new Timestamp(System.currentTimeMillis());
        this.role = ROLE.USER;
//        if (this.role == null) {
//            this.role = new Role(ROLE.USER, this);
//        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_at = new Timestamp(System.currentTimeMillis());
    }
}