package com.hazlin.transactionmanagement.controller;

import com.hazlin.transactionmanagement.dto.TransactionDTO;
import com.hazlin.transactionmanagement.mapper.TransactionMapper;
import com.hazlin.transactionmanagement.model.Transaction;
import com.hazlin.transactionmanagement.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.toEntity(transactionDTO);
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(TransactionMapper.toDTO(createdTransaction), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> getAllTransactions(Pageable pageable) {
        Page<Transaction> transactions = transactionService.getAllTransactions(pageable);
        Page<TransactionDTO> transactionDTOS = transactions.map(TransactionMapper::toDTO);
        return new ResponseEntity<>(transactionDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return new ResponseEntity<>(TransactionMapper.toDTO(transaction), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.toEntity(transactionDTO);
        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
        return new ResponseEntity<>(TransactionMapper.toDTO(updatedTransaction), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
