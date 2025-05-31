package com.example.Intern.Handle;

import com.example.Intern.Entity.Verification;
import com.example.Intern.Repository.VerificationRepository;
import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class VerificationHandle {

    @Autowired
    private VerificationRepository verificationRepository;

    // Tìm tất cả các yêu cầu kiểm chứng
    public List<Verification> findVerificationDetails() {
        return verificationRepository.findVerificationDetails();
    }

    // Tìm kiểm chứng theo ID bất động sản
    public Verification findVerificationByPropertyId(Long propertyId) {
        return verificationRepository.findById(propertyId).orElse(null);  // Trả về đối tượng duy nhất
    }

    public Verification save(Verification verification) {
        return verificationRepository.save(verification);
    }

    // Tìm kiểm chứng theo ID nhân viên kiểm chứng
    public List<Verification> findVerificationsByVerifierId(Long verifierId) {
        return verificationRepository.findVerificationsByVerifierId(verifierId);
    }

    // Xóa kiểm chứng theo ID
    public void deleteVerificationById(Long verificationId) {
        verificationRepository.deleteById(verificationId);  // Xóa dịch vụ kiểm chứng
    }

    // Tìm kiểm chứng theo ID bất động sản
    public List<Verification> findVerificationsByPropertyId(Long propertyId) {
        return verificationRepository.findVerificationsByPropertyId(propertyId);
    }

    // Phương thức tìm kiếm Verification theo ID
    public Verification findVerificationById(Long verificationId) {
        return verificationRepository.findById(verificationId).orElse(null);  // Trả về null nếu không tìm thấy
    }

    // Phương thức để lọc các xác minh theo người xác minh và trạng thái
    // Lọc xác minh theo người xác minh và trạng thái

    public List<Verification> findAll() {
        return verificationRepository.findAll();
    }
}
