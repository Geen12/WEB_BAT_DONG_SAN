package com.example.Intern.Controller;

import com.example.Intern.Entity.*;
import com.example.Intern.Handle.LegalSupportHandle;
import com.example.Intern.Handle.PropertyHandle;
import com.example.Intern.Handle.TransactionHandle;
import com.example.Intern.Handle.UserHandle;
import com.example.Intern.Handle.VerificationHandle;
import com.example.Intern.Model.UserDTO;
import com.example.Intern.Repository.TransactionRepository;
import com.example.Intern.Repository.UserRepository;
import com.example.Intern.Utility.Enum.STATUS_LEGAL;
import com.example.Intern.Utility.Enum.STATUS_TRANSACTION;
import com.example.Intern.Utility.Enum.STATUS_USER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserHandle userHandle;
    @Autowired
    private PropertyHandle propertyHandle;

    @Autowired
    private LegalSupportHandle legalSupportHandle;

    @Autowired
    private TransactionHandle transactionHandle;

    @Autowired
    private VerificationHandle verificationHandle;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    //**********************************************Sign up
    @GetMapping("/signup")
    public String signUpAdminPage(Model model) {
        model.addAttribute("userDTO", new UserDTO()); // Truyền đối tượng UserDTO rỗng vào model
        return "admin_signup";  // Trả về trang đăng ký admin
    }


    // Đăng ký admin
    @PostMapping("/signup")
    public String createAdmin(@ModelAttribute UserDTO userDTO) {
        if (userDTO != null) {
            User user = new User();
            user.setUser_name(userDTO.getUserName());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));  // Mã hóa mật khẩu
            user.setFull_name(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setPhone_number(userDTO.getPhoneNumber());
            user.setAddress(userDTO.getAddress());
            user.setDate_of_birth(userDTO.getDateOfBirth());
            user.setGender(userDTO.getGender());
            user.setRole(userDTO.getRole());
            user.setStatusUser(STATUS_USER.CONFIRMED);  // Đặt trạng thái là đã xác nhận
            user.setAccept_terms(true);

            // Lưu admin vào cơ sở dữ liệu
            userRepository.save(user);

            return "redirect:/admin";  // Chuyển hướng đến trang quản trị sau khi đăng ký thành công
        }

        return "redirect:/signup/admin?error=true";  // Nếu có lỗi, quay lại trang đăng ký admin
    }

    //***********************************************USER
    // Lấy tất cả người dùng và hiển thị trên trang web
    @GetMapping({"", "/","/users"})
    public String getAllUsers(Model model) {
        // Lấy tất cả người dùng với trạng thái CONFIRMED
        List<User> users = userHandle.findUsersByStatus(STATUS_USER.CONFIRMED);
        model.addAttribute("users", users); // Truyền dữ liệu vào model để hiển thị
        return "admin_dashboard"; // Trả về tên trang Thymeleaf
    }

    // Cập nhật trạng thái người dùng
    @PutMapping("/user/status/{userName}")
    public ResponseEntity<Void> updateUserStatus(@PathVariable String userName, @RequestBody STATUS_USER statusUser) {
        userHandle.updateUserStatusByUserName(userName, statusUser);
        return ResponseEntity.ok().build();
    }

    // Xóa người dùng
    @DeleteMapping("/user/{userName}")
    public String deleteUser(@PathVariable String userName) {
        userHandle.deleteUserByUserName(userName);
        return "redirect:/admin/users";  // Chuyển hướng lại trang danh sách người dùng
    }

    // Tạo mới người dùng
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userHandle.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    //***********************************************PROPERTY
    // Hiển thị tất cả bất động sản
    @GetMapping("/properties")
    public String getAllProperties(Model model) {
        List<Property> properties = propertyHandle.findPropertyDetails();
        model.addAttribute("properties", properties);  // Truyền dữ liệu vào model
        return "admin_property";  // Chỉ định trang Thymeleaf
    }

    // Hiển thị thông tin bất động sản theo ID
    @GetMapping("/properties/{propertyId}")
    public String getPropertyById(@PathVariable Long propertyId, Model model) {
        Property property = propertyHandle.findPropertyById(propertyId);
        model.addAttribute("property", property);
        return "admin_property_details";  // Trả về trang chi tiết bất động sản
    }

    // Thêm mới bất động sản
    @GetMapping("/properties/create")
    public String showCreatePropertyForm(Model model) {
        Property property = new Property();
        model.addAttribute("property", property);  // Truyền đối tượng rỗng để tạo mới
        return "admin_property_create";  // Trang thêm mới bất động sản
    }

    @PostMapping("/properties")
    public String createProperty(@ModelAttribute Property property) {
        propertyHandle.createProperty(property);  // Thêm bất động sản mới vào cơ sở dữ liệu
        return "redirect:/admin/properties";  // Chuyển hướng đến trang danh sách bất động sản
    }

    // Cập nhật bất động sản
    @GetMapping("/properties/edit/{propertyId}")
    public String showEditPropertyForm(@PathVariable Long propertyId, Model model) {
        Property property = propertyHandle.findPropertyById(propertyId);
        model.addAttribute("property", property);
        return "admin_property_edit";  // Trang chỉnh sửa bất động sản
    }

    @PostMapping("/properties/update/{propertyId}")
    public String updateProperty(@PathVariable Long propertyId, @ModelAttribute Property property) {
        property.setId(propertyId);
        propertyHandle.updateProperty(property);  // Cập nhật bất động sản
        return "redirect:/admin/properties";  // Chuyển hướng về danh sách bất động sản
    }

    // Xóa bất động sản
    @DeleteMapping("/properties/{propertyId}")
    public String deleteProperty(@PathVariable Long propertyId) {
        propertyHandle.deletePropertyById(propertyId);  // Xóa bất động sản
        return "redirect:/admin/properties";  // Chuyển hướng về danh sách bất động sản
    }

    //***********************************************LEGAL
    // Hiển thị tất cả các dịch vụ hỗ trợ pháp lý
    @GetMapping("/legalsupport")
    public String getLegalSupports(@RequestParam(value = "status", required = false) String status, Model model) {
        List<LegalSupport> legalSupports;
        if (status != null && !status.isEmpty()) {
            // Convert status string to enum
            STATUS_LEGAL statusEnum = STATUS_LEGAL.valueOf(status.toUpperCase());
            legalSupports = legalSupportHandle.findLegalSupportByStatus(statusEnum);
        } else {
            legalSupports = legalSupportHandle.findLegalSupportDetails();
        }
        model.addAttribute("legalSupports", legalSupports);
        return "admin_legal_support_list";  // Trang danh sách
    }

    // Hiển thị chi tiết dịch vụ hỗ trợ pháp lý theo ID
    @GetMapping("/legalsupport/{legalSupportId}")
    public String getLegalSupportById(@PathVariable Long legalSupportId, Model model) {
        LegalSupport legalSupport = legalSupportHandle.findLegalSupportByUserId(legalSupportId);  // Lấy một đối tượng duy nhất
        model.addAttribute("legalSupport", legalSupport);  // Truyền đối tượng duy nhất vào model
        return "admin_legal_support_details";  // Trang chi tiết
    }

    //***********************************************TRANSACTION
    // Hiển thị tất cả giao dịch
    @GetMapping("/transactions")
    public String getTransactions(@RequestParam(value = "status", required = false) String status, Model model) {
        List<Transaction> transactions;

        if (status != null && !status.isEmpty()) {
            try {
                // Convert the string status to the corresponding enum value
                STATUS_TRANSACTION transactionStatus = STATUS_TRANSACTION.valueOf(status.toUpperCase());
                transactions = transactionRepository.findByStatus(transactionStatus);
            } catch (IllegalArgumentException e) {
                // If the status is invalid, handle it appropriately (e.g., return all transactions)
                transactions = transactionRepository.findAll();
            }
        } else {
            transactions = transactionRepository.findAll();  // If no status is selected, get all transactions
        }

        model.addAttribute("transactions", transactions);
        model.addAttribute("status", status);  // Keep the selected status in the form
        return "admin_transaction_list";  // Return the transactions view
    }

    // Hiển thị chi tiết giao dịch theo ID
    @GetMapping("/transactions/{transactionId}")
    public String getTransactionById(@PathVariable Long transactionId, Model model) {
        Transaction transaction = transactionHandle.findTransactionById(transactionId);
        model.addAttribute("transaction", transaction);
        return "admin_transaction_details";  // Trả về trang chi tiết giao dịch
    }

    // Xóa giao dịch
    @DeleteMapping("/transactions/{transactionId}")
    public String deleteTransaction(@PathVariable Long transactionId) {
        transactionHandle.deleteTransactionById(transactionId);  // Xóa giao dịch
        return "redirect:/admin/transactions";  // Chuyển hướng về trang danh sách giao dịch
    }

    //***********************************************VERIFICATION
    // Hiển thị tất cả các dịch vụ kiểm chứng
    @GetMapping("/verifications")
    public String getAllVerifications(Model model) {
        List<Verification> verifications = verificationHandle.findVerificationDetails();
        model.addAttribute("verifications", verifications);  // Truyền dữ liệu vào model
        return "admin_verification_list";  // Trả về trang danh sách kiểm chứng
    }

    // Hiển thị chi tiết dịch vụ kiểm chứng theo ID
    @GetMapping("/verifications/{verificationId}")
    public String getVerificationById(@PathVariable Long verificationId, Model model) {
        Verification verification = verificationHandle.findVerificationByPropertyId(verificationId);
        model.addAttribute("verification", verification);
        return "admin_verification_details";  // Trang chi tiết kiểm chứng
    }

    // Xóa dịch vụ kiểm chứng
    @DeleteMapping("/verifications/{verificationId}")
    public String deleteVerification(@PathVariable Long verificationId) {
        verificationHandle.deleteVerificationById(verificationId);  // Xóa dịch vụ kiểm chứng
        return "redirect:/admin/verifications";  // Chuyển hướng về trang danh sách kiểm chứng
    }
}