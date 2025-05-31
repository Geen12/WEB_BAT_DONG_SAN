package com.example.Intern.Repository;

import com.example.Intern.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Phương thức truy vấn tất cả các thanh toán
    @Query("SELECT p FROM Payment p")
    List<Payment> findPaymentDetails();

    // Phương thức tìm thanh toán theo ID
    Optional<Payment> findById(Long paymentId);  // Phương thức findById đã có sẵn trong JpaRepository

    // Phương thức tìm thanh toán theo ID người dùng
    @Query("SELECT p FROM Payment p WHERE p.user.id = :userId")
    List<Payment> findPaymentsByUserId(@Param("userId") Long userId);

    // Phương thức tìm thanh toán theo ID giao dịch
    @Query("SELECT p FROM Payment p WHERE p.transaction.id = :transactionId")
    List<Payment> findPaymentsByTransactionId(@Param("transactionId") Long transactionId);
}
