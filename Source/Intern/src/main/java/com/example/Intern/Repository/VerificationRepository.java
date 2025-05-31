package com.example.Intern.Repository;

import com.example.Intern.Entity.Verification;
import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    // Phương thức tìm kiểm chứng theo ID bất động sản
    @Query("SELECT v FROM Verification v WHERE v.property.id = :propertyId")
    List<Verification> findVerificationsByPropertyId(@Param("propertyId") Long propertyId);

    // Phương thức truy vấn tất cả các yêu cầu kiểm chứng
    @Query("SELECT v FROM Verification v")
    List<Verification> findVerificationDetails();

    // Phương thức tìm kiểm chứng theo ID bất động sản
    @Query("SELECT v FROM Verification v WHERE v.property.id = :propertyId")
    Verification findVerificationByPropertyId(@Param("propertyId") Long propertyId);

    // Phương thức tìm kiểm chứng theo ID nhân viên kiểm chứng
    @Query("SELECT v FROM Verification v WHERE v.verifier.id = :verifierId")
    List<Verification> findVerificationsByVerifierId(@Param("verifierId") Long verifierId);

}
