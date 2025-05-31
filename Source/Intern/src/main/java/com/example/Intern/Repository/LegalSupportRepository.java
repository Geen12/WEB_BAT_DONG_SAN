package com.example.Intern.Repository;

import com.example.Intern.Entity.LegalSupport;
import com.example.Intern.Entity.Property;
import com.example.Intern.Entity.Transaction;
import com.example.Intern.Utility.Enum.STATUS_LEGAL;
import com.example.Intern.Utility.Enum.STATUS_TRANSACTION;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LegalSupportRepository extends JpaRepository<LegalSupport, Long> {

    // Truy vấn để lấy tất cả các dịch vụ hỗ trợ pháp lý
    @Query("SELECT ls FROM LegalSupport ls")
    List<LegalSupport> findLegalSupportDetails();

    // Tìm dịch vụ hỗ trợ pháp lý theo ID người dùng
    Optional<LegalSupport> findById(Long id);

    // Truy vấn tìm các dịch vụ hỗ trợ pháp lý theo ID người dùng
    @Query("SELECT ls FROM LegalSupport ls WHERE ls.user.id = :userId")
    List<LegalSupport> findLegalSupportByUserId(@Param("userId") Long userId);

    // Truy vấn tìm các dịch vụ hỗ trợ pháp lý theo ID bất động sản
    @Query("SELECT ls FROM LegalSupport ls WHERE ls.property.id = :propertyId")
    List<LegalSupport> findLegalSupportByPropertyId(@Param("propertyId") Long propertyId);

    LegalSupport findByProperty(Property property);

    List<LegalSupport> findByStatus(STATUS_LEGAL status);
}
