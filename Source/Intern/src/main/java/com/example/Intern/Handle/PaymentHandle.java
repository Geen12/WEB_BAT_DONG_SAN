package com.example.Intern.Handle;

import com.example.Intern.Entity.Payment;
import com.example.Intern.Repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PaymentHandle {

    @Autowired
    private PaymentRepository paymentRepository;

    // Tìm tất cả thanh toán
    public List<Payment> findPaymentDetails() {
        return paymentRepository.findPaymentDetails();
    }

    // Tìm thanh toán theo ID
    public Payment findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null); // Truy vấn thanh toán theo ID
    }

    // Tìm thanh toán theo ID người dùng
    public List<Payment> findPaymentsByUserId(Long userId) {
        return paymentRepository.findPaymentsByUserId(userId);
    }

    // Tìm thanh toán theo ID giao dịch
    public List<Payment> findPaymentsByTransactionId(Long transactionId) {
        return paymentRepository.findPaymentsByTransactionId(transactionId);
    }
}
