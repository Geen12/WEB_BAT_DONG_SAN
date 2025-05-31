package com.example.Intern.Controller;

import com.example.Intern.Entity.LegalSupport;
import com.example.Intern.Entity.Property;
import com.example.Intern.Entity.User;
import com.example.Intern.Entity.Verification;
import com.example.Intern.Handle.LegalSupportHandle;
import com.example.Intern.Handle.VerificationHandle;
import com.example.Intern.Repository.UserRepository;
import com.example.Intern.Utility.Enum.STATUS_LEGAL;
import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/verifications")
public class VerificationController {
    @Autowired
    private VerificationHandle verificationHandle;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LegalSupportHandle legalSupportHandle;

    // Lấy tất cả kiểm chứng
    @GetMapping("/")
    public List<Verification> getAllVerifications() {
        return verificationHandle.findVerificationDetails();
    }

    // Lấy kiểm chứng theo ID bất động sản
    @GetMapping("/property/{propertyId}")
    public List<Verification> getVerificationsByPropertyId(@PathVariable Long propertyId) {
        return verificationHandle.findVerificationsByPropertyId(propertyId);
    }

    // Lấy kiểm chứng theo ID nhân viên kiểm chứng
    @GetMapping("/verifier/{verifierId}")
    public List<Verification> getVerificationsByVerifierId(@PathVariable Long verifierId) {
        return verificationHandle.findVerificationsByVerifierId(verifierId);
    }

    @PostMapping("/{verificationId}/accept")
    public RedirectView acceptVerification(@PathVariable Long verificationId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();
        User verifier = userRepository.findUserByUserName(username);

        // Tìm đối tượng Verification
        Verification verification = verificationHandle.findVerificationById(verificationId);

        if (verification != null) {
            // Cập nhật verifier cho verification
            verification.setVerifier(verifier);
            verification.setVerificationDate(new Timestamp(System.currentTimeMillis()));// Hoặc bất kỳ trạng thái nào bạn muốn
            verificationHandle.save(verification);  // Lưu lại đối tượng Verification đã được cập nhật
        }

        // Quay lại trang quản lý verifications
        return new RedirectView("/admin/verifications");
    }

    @PostMapping("/{verificationId}/approved")
    public RedirectView approvedVerification(@PathVariable Long verificationId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();
        User verifier = userRepository.findUserByUserName(username);

        // Tìm đối tượng Verification
        Verification verification = verificationHandle.findVerificationById(verificationId);

        if (verification != null) {
            Property property = verification.getProperty();
            property.setVerificationStatus(STATUS_VERIFICATION.APPROVED);

            // Cập nhật trạng thái xác minh
            verification.setVerifier(verifier);  // Đặt verifier là người dùng hiện tại
            verification.setVerificationDate(new Timestamp(System.currentTimeMillis()));

            // Lưu đối tượng Verification đã cập nhật
            verificationHandle.save(verification);
        }

        // Quay lại trang quản lý verifications
        return new RedirectView("/admin/verifications");
    }

    @PostMapping("/{verificationId}/rejected")
    public RedirectView rejectedVerification(@PathVariable Long verificationId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();
        User verifier = userRepository.findUserByUserName(username);

        // Tìm đối tượng Verification
        Verification verification = verificationHandle.findVerificationById(verificationId);
        Property property = verification.getProperty();
        property.setVerificationStatus(STATUS_VERIFICATION.REJECTED);

        if (verification != null) {
            // Cập nhật verifier cho verification
            verification.setProperty(property);
            verification.setVerificationDate(new Timestamp(System.currentTimeMillis()));// Hoặc bất kỳ trạng thái nào bạn muốn
            verificationHandle.save(verification);  // Lưu lại đối tượng Verification đã được cập nhật
            try {
                LegalSupport legalSupport = legalSupportHandle.findByProperty(property);
                legalSupport.setStatus(STATUS_LEGAL.CANCELLED);
                legalSupportHandle.save(legalSupport);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // Quay lại trang quản lý verifications

        return new RedirectView("/admin/verifications");
    }
}