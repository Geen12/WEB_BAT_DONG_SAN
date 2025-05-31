package com.example.Intern.Controller;

import com.example.Intern.Entity.*;
import com.example.Intern.Handle.LegalSupportHandle;
import com.example.Intern.Handle.PropertyHandle;
import com.example.Intern.Handle.TransactionHandle;
import com.example.Intern.Handle.VerificationHandle;
import com.example.Intern.Repository.UserRepository;
import com.example.Intern.Utility.Enum.STATUS_LEGAL;
import com.example.Intern.Utility.Enum.STATUS_TRANSACTION;
import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyHandle propertyHandle;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationHandle verificationHandle;

    @Autowired
    private TransactionHandle transactionHandle;

    @Autowired
    private LegalSupportHandle legalSupportHandle;

    @PostMapping("/addToLegal/{propertyId}")
    public RedirectView addToLegalSupport(@PathVariable Long propertyId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();

        LegalSupport legalSupport = new LegalSupport(null, null, userRepository.findUserByUserName(username),
                propertyHandle.findPropertyById(propertyId), "Chưa có thông tin", STATUS_LEGAL.PENDING,
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        legalSupportHandle.save(legalSupport);
        return new RedirectView("/pay");
    }

    // Hiển thị tất cả bất động sản hoặc tìm kiếm theo tiêu chí
    @GetMapping({"", "/"})
    public String getAllProperties(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(required = false) String title,
                                   @RequestParam(required = false) String location,
                                   @RequestParam(required = false) Double area,
                                   @RequestParam(defaultValue = "APPROVED") STATUS_VERIFICATION verificationStatus,
                                   Model model) {

        Pageable pageable = PageRequest.of(page - 1, 9); // Lấy 9 bất động sản mỗi lần
        Page<Property> properties;

        // Kiểm tra nếu có các tham số tìm kiếm
        if (title != null || location != null || area != null) {
            properties = propertyHandle.searchProperties(title, location, area, pageable);
        } else {
            properties = propertyHandle.findPropertyDetailsByStatus(verificationStatus, pageable);
        }

        model.addAttribute("properties", properties);  // Truyền dữ liệu phân trang vào model
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", properties.getTotalPages());
        return "properties";  // Chỉ định trang Thymeleaf
    }

    // Hiển thị chi tiết bất động sản
    @GetMapping("/property-details/{propertyId}")
    public String propertyDetails(@PathVariable Long propertyId, Model model) {
        Property property = propertyHandle.findPropertyById(propertyId);
        model.addAttribute("property", property);
        return "property-details";  // Trả về trang property-details
    }

    // Thêm bất động sản (Form)
    @GetMapping("/add")
    public String showAddPropertyForm(Model model) {
        model.addAttribute("property", new Property()); // Tạo một đối tượng Property mới cho form
        return "add-property"; // Trang thêm bất động sản
    }

    @PostMapping("/add")
    public String addProperty(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("price") Double price,
                              @RequestParam("area") Double area,
                              @RequestParam("location") String location,
                              @RequestParam("images") List<MultipartFile> images,
                              HttpServletRequest request, Model model) throws IOException {

        // Lấy thông tin người dùng đã đăng nhập
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findUserByUserName(username);

        // Tạo đối tượng bất động sản
        Property property = new Property();
        property.setUser(user);
        property.setTitle(title);
        property.setDescription(description);
        property.setPrice(price);
        property.setArea(area);
        property.setLocation(location);

        // Lưu bất động sản để tạo ID
        property = propertyHandle.saveProperty(property); // Lưu property và lấy đối tượng đã được cập nhật với id

        // Cấu hình thư mục lưu ảnh
        String uploadDir = "D:/Photo/" + property.getId();  // Lưu ảnh vào D:/Photo/property/{id}
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Lưu ảnh và thêm tên ảnh vào danh sách
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);
            if (!image.isEmpty()) {
                String imageName = (i + 1) + ".jpg";  // Lưu ảnh theo thứ tự 1.jpg, 2.jpg,...
                Path path = Paths.get(uploadDir, imageName);
                image.transferTo(path);  // Lưu ảnh vào thư mục
                imageUrls.add(imageName);  // Thêm tên ảnh vào danh sách
            }
        }

        // Cập nhật đối tượng bất động sản với danh sách ảnh
        property.setImageUrls(imageUrls);
        property.setImageUrlAfterSave();// Phương thức để cập nhật ảnh
        propertyHandle.saveProperty(property);  // Lưu lại đối tượng bất động sản với ảnh

        // Tạo và lưu đối tượng Transaction
        Transaction transaction = new Transaction();
        transaction.setBuyer(user);  // Buyer là người đăng nhập
        transaction.setTransactionAmount(2.0);  // Số tiền là 2 triệu đồng
        transaction.setProperty(property);
        transaction.setStatus(STATUS_TRANSACTION.PENDING);// Gán bất động sản vào giao dịch

        // Lưu giao dịch vào cơ sở dữ liệu
        transactionHandle.save(transaction);

        // Lưu đối tượng Verification
        verificationHandle.save(new Verification(null, property, null, "Chưa ghi gì cả", new Timestamp(System.currentTimeMillis())));

        return "redirect:/properties";  // Quay lại danh sách bất động sản
    }
}