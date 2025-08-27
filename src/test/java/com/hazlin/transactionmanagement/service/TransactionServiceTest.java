package com.hazlin.transactionmanagement.service;

import com.hazlin.transactionmanagement.exception.TransactionNotFoundException;
import com.hazlin.transactionmanagement.model.Transaction;
import com.hazlin.transactionmanagement.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(100.0, "CREDIT", "Test transaction", LocalDateTime.now());
        transaction.setId(1L);
    }

    @Test
    void createTransaction_shouldReturnCreatedTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        assertNotNull(createdTransaction);
        assertEquals(transaction.getId(), createdTransaction.getId());
    }

    @Test
    void deleteTransaction_shouldCallDeleteById() {
        when(transactionRepository.existsById(1L)).thenReturn(true);
        transactionService.deleteTransaction(1L);
        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTransaction_shouldThrowException_whenTransactionNotFound() {
        when(transactionRepository.existsById(1L)).thenReturn(false);
        assertThrows(TransactionNotFoundException.class, () -> transactionService.deleteTransaction(1L));
    }

    @Test
    void updateTransaction_shouldReturnUpdatedTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Transaction updatedTransaction = transactionService.updateTransaction(1L, transaction);
        assertNotNull(updatedTransaction);
        assertEquals(transaction.getId(), updatedTransaction.getId());
    }

    @Test
    void updateTransaction_shouldThrowException_whenTransactionNotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TransactionNotFoundException.class, () -> transactionService.updateTransaction(1L, transaction));
    }

    @Test
    void getAllTransactions_shouldReturnPageOfTransactions() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Transaction> transactionPage = new PageImpl<>(Collections.singletonList(transaction));
        when(transactionRepository.findAll(pageable)).thenReturn(transactionPage);
        Page<Transaction> result = transactionService.getAllTransactions(pageable);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getTransactionById_shouldReturnTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        Transaction result = transactionService.getTransactionById(1L);
        assertNotNull(result);
        assertEquals(transaction.getId(), result.getId());
    }

    @Test
    void getTransactionById_shouldThrowException_whenTransactionNotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TransactionNotFoundException.class, () -> transactionService.getTransactionById(1L));
    }
}
