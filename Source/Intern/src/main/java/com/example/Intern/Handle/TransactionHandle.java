package com.example.Intern.Handle;

import com.example.Intern.Entity.Transaction;
import com.example.Intern.Repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TransactionHandle {

    @Autowired
    private TransactionRepository transactionRepository;

    // Tìm giao dịch theo ID người mua
    public List<Transaction> findTransactionsByBuyerId(Long buyerId) {
        return transactionRepository.findTransactionsByBuyerId(buyerId);
    }

    // Tìm giao dịch theo ID bất động sản
    public List<Transaction> findTransactionsByPropertyId(Long propertyId) {
        return transactionRepository.findTransactionsByPropertyId(propertyId);
    }

    // Tìm tất cả các giao dịch
    public List<Transaction> findTransactionDetails() {
        return transactionRepository.findTransactionDetails();
    }

    // Tìm giao dịch theo ID
    public Transaction findTransactionById(Long transactionId) {
        return transactionRepository.findTransactionById(transactionId);
    }

    // Xóa giao dịch theo ID
    public void deleteTransactionById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
