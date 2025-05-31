package com.example.Intern.Controller;

import com.example.Intern.Entity.*;
import com.example.Intern.Handle.PropertyHandle;
import com.example.Intern.Handle.TransactionHandle;
import com.example.Intern.Utility.Enum.STATUS_LEGAL;
import com.example.Intern.Utility.Enum.STATUS_TRANSACTION;
import com.example.Intern.Utility.Enum.STATUS_VERIFICATION;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionHandle transactionHandle;

    @Autowired
    private PropertyHandle propertyHandle;

    @GetMapping("")
    public String a() {
        return "a";
    }


    // Lấy tất cả giao dịch
    @GetMapping("/")
    public List<Transaction> getAllTransactions() {
        return transactionHandle.findTransactionDetails();
    }

    // Lấy giao dịch theo ID
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Transaction transaction = transactionHandle.findTransactionById(transactionId);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    // Lấy giao dịch của người mua
    @GetMapping("/buyer/{buyerId}")
    public List<Transaction> getTransactionsByBuyerId(@PathVariable Long buyerId) {
        return transactionHandle.findTransactionsByBuyerId(buyerId);
    }

    // Lấy giao dịch theo bất động sản
    @GetMapping("/property/{propertyId}")
    public List<Transaction> getTransactionsByPropertyId(@PathVariable Long propertyId) {
        return transactionHandle.findTransactionsByPropertyId(propertyId);
    }

    @PostMapping("/{transactionId}/accept")
    public RedirectView rejectedVerification(@PathVariable Long transactionId, HttpServletRequest request) {
        // Lấy thông tin người dùng hiện tại
        String username = request.getUserPrincipal().getName();

        // Tìm đối tượng Verification
        Transaction transaction = transactionHandle.findTransactionById(transactionId);

        if (transaction != null) {
            // Cập nhật verifier cho verification
            transaction.setStatus(STATUS_TRANSACTION.COMPLETED);
            transactionHandle.save(transaction);

            Property property = transaction.getProperty();
            property.setVerificationStatus(STATUS_VERIFICATION.PENDING);
            propertyHandle.saveProperty(property);
        }

        // Quay lại trang quản lý verifications

        return new RedirectView("/admin/transactions");
    }
}