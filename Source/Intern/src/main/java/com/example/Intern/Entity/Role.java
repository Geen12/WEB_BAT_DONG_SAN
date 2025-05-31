//package com.example.Intern.Entity;
//
//import com.example.Intern.Utility.Enum.ROLE;
//import com.example.Intern.Utility.constants.ColumnName;
//import com.example.Intern.Utility.constants.TableName;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = TableName.ROLE)
//public class Role {
//    @Id
//    @GeneratedValue
//    @Column(name = ColumnName.Role.ID)
//    private Long id;
//
//    @Column(name = ColumnName.Role.ROLE, nullable = false)
//    private ROLE role;
//
//    @OneToOne
//    @JoinColumn(name = ColumnName.Role.USER_ID, referencedColumnName = ColumnName.Users.ID, unique = true, nullable = false)
//    private User user;
//
//    public Role(ROLE role, User user) {
//        this.role = role;
//        this.user = user;
//    }
//}
