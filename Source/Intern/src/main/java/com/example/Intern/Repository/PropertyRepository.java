package com.example.Intern.Repository;

import com.example.Intern.Entity.Property;
import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Truy vấn tìm bất động sản của người dùng theo ID
    @Query("SELECT p FROM Property p WHERE p.user.id = :userId")
    List<Property> findPropertiesByUserId(@Param("userId") Long userId);

    // Truy vấn tìm bất động sản theo trạng thái xác minh
    @Query("SELECT p FROM Property p WHERE p.verificationStatus = :verificationStatus")
    List<Property> findPropertiesByVerificationStatus(@Param("verificationStatus") String verificationStatus);

    // Truy vấn tất cả bất động sản
    @Query("SELECT p FROM Property p")
    List<Property> findPropertyDetails();

    // Tìm bất động sản theo ID
    @Query("SELECT p FROM Property p WHERE p.id = :propertyId")
    Property findPropertyById(@Param("propertyId") Long propertyId);

    Page<Property> findByTitleContainingOrLocationContainingOrAreaEquals(String title, String location, Double area, Pageable pageable);

    Page<Property> findByVerificationStatus(STATUS_VERIFICATION verificationStatus, Pageable pageable);
}
