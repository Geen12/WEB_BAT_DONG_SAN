package com.example.Intern.Repository;

import com.example.Intern.Entity.Transaction;
import com.example.Intern.Utility.Enum.STATUS_TRANSACTION;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Truy vấn tất cả giao dịch
    @Query("SELECT t FROM Transaction t")
    List<Transaction> findTransactionDetails();

    // Tìm giao dịch theo ID
    @Query("SELECT t FROM Transaction t WHERE t.id = :transactionId")
    Transaction findTransactionById(@Param("transactionId") Long transactionId);

    // Tìm giao dịch theo ID người mua
    @Query("SELECT t FROM Transaction t WHERE t.buyer.id = :buyerId")
    List<Transaction> findTransactionsByBuyerId(@Param("buyerId") Long buyerId);

    // Tìm giao dịch theo ID bất động sản
    @Query("SELECT t FROM Transaction t WHERE t.property.id = :propertyId")
    List<Transaction> findTransactionsByPropertyId(@Param("propertyId") Long propertyId);

    List<Transaction> findByStatus(STATUS_TRANSACTION status);
}
