package com.example.MinorProjectDigitalLibrary.repository;

import com.example.MinorProjectDigitalLibrary.models.Book;
import com.example.MinorProjectDigitalLibrary.models.Student;
import com.example.MinorProjectDigitalLibrary.models.Transaction;
import com.example.MinorProjectDigitalLibrary.models.enums.TransactionStatus;
import com.example.MinorProjectDigitalLibrary.models.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(Student student, Book book, TransactionType issue, TransactionStatus success);
}
