package com.example.Intern.Handle;

import com.example.Intern.Entity.Property;
import com.example.Intern.Repository.PropertyRepository;
import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class PropertyHandle {
    @Autowired
    private PropertyRepository propertyRepository;

    // Tìm tất cả bất động sản
    public List<Property> findPropertyDetails() {
        return propertyRepository.findPropertyDetails();  // Lấy danh sách bất động sản từ cơ sở dữ liệu
    }

    // Cập nhật hoặc tạo bất động sản mới
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    // Cập nhật bất động sản
    public Property updateProperty(Property property) {
        return propertyRepository.save(property);
    }

    // Xóa bất động sản theo ID
    public void deletePropertyById(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }

    // Lấy danh sách bất động sản với phân trang
    public Page<Property> getProperties(Pageable pageable) {
        return propertyRepository.findAll(pageable);  // Sử dụng phương thức findAll của JpaRepository để phân trang
    }

    // Tìm kiếm bất động sản theo tiêu chí
    public Page<Property> searchProperties(String title, String location, Double area, Pageable pageable) {
        return propertyRepository.findByTitleContainingOrLocationContainingOrAreaEquals(title, location, area, pageable);
    }

    // Lấy danh sách bất động sản (không tìm kiếm)
    public Page<Property> findPropertyDetails(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }

    // Lấy bất động sản theo ID
    public Property findPropertyById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    @PostConstruct
    public void init() {
        // Code được chạy ngay sau khi bean PropertyHandler được tạo
        System.out.println("PropertyHandler has been initialized!");

        // Ví dụ: Xử lý lại các tài nguyên tĩnh hoặc làm mới ảnh khi ứng dụng khởi động
        String uploadDir = "src/main/resources/static/photo/property/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Thực hiện thêm bất động sản
    public void addProperty(Property property, List<MultipartFile> images) throws IOException {
        // Logic thêm bất động sản vào database
        propertyRepository.save(property);

        // Sau khi bất động sản được lưu vào DB, thực hiện lưu ảnh
        String uploadDir = "src/main/resources/static/photo/property/" + property.getId();
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Lưu ảnh vào thư mục
        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);
            if (!image.isEmpty()) {
                String imageName = (i + 1) + ".jpg";
                Path path = Paths.get(uploadDir, imageName);
                image.transferTo(path);
            }
        }
    }

    public Page<Property> findPropertyDetailsByStatus(STATUS_VERIFICATION verificationStatus, Pageable pageable) {
        // Tạo query để tìm bất động sản chỉ có trạng thái APPROVED
        return propertyRepository.findByVerificationStatus(verificationStatus, pageable);
    }
}
