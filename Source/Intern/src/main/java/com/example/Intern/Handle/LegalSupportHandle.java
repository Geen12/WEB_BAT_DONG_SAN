package com.example.Intern.Handle;

import com.example.Intern.Entity.LegalSupport;
import com.example.Intern.Entity.Property;
import com.example.Intern.Repository.LegalSupportRepository;
import com.example.Intern.Utility.Enum.STATUS_LEGAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LegalSupportHandle {

    @Autowired
    private LegalSupportRepository legalSupportRepository;

    // Lấy tất cả các dịch vụ hỗ trợ pháp lý
    public List<LegalSupport> findLegalSupportDetails() {
        return legalSupportRepository.findLegalSupportDetails();  // Trả về danh sách
    }

    // Tìm các dịch vụ hỗ trợ pháp lý theo ID người dùng
    public LegalSupport findLegalSupportByUserId(Long userId) {
        return legalSupportRepository.findById(userId).orElse(null);  // Trả về một đối tượng duy nhất
    }

    // Tìm các dịch vụ hỗ trợ pháp lý theo ID bất động sản
    public List<LegalSupport> findLegalSupportByPropertyId(Long propertyId) {
        return legalSupportRepository.findLegalSupportByPropertyId(propertyId);
    }

    public LegalSupport save(LegalSupport legalSupport) {
        return legalSupportRepository.save(legalSupport);
    }

    // Tìm kiếm LegalSupport theo Property
    public LegalSupport findByProperty(Property property) {
        return legalSupportRepository.findByProperty(property);
    }

    public void delete(LegalSupport legalSupport) {
        legalSupportRepository.delete(legalSupport);
    }

    public List<LegalSupport> findLegalSupportByStatus(STATUS_LEGAL status) {
        return legalSupportRepository.findByStatus(status);
    }
}
