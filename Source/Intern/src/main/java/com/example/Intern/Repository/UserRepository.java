
package com.example.Intern.Repository;

import com.example.Intern.Entity.User;
import com.example.Intern.Utility.Enum.STATUS_USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.user_name = :userName")
    User findUserByUserName(@Param("userName") String userName);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("SELECT u.user_name, u.password, u.role FROM User u")
    List<Object[]> findUserNameAndPassword();

    // Truy vấn lấy người dùng theo trạng thái
    @Query("SELECT u FROM User u WHERE u.statusUser = :statusUser")
    List<User> findUsersByStatus(@Param("statusUser") STATUS_USER statusUser);

    // Cập nhật trạng thái người dùng
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.statusUser = :statusUser WHERE u.user_name = :userName")
    void updateStatusByUserName(@Param("userName") String userName, @Param("statusUser") STATUS_USER statusUser);

    // Phương thức xóa người dùng theo tên đăng nhập
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.user_name = :userName")
    void deleteByUserName(@Param("userName") String userName);
}
