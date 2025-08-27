package com.hazlin.transactionmanagement.service;

import com.hazlin.transactionmanagement.exception.TransactionNotFoundException;
import com.hazlin.transactionmanagement.model.Transaction;
import com.hazlin.transactionmanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @CacheEvict(value = "transactions", allEntries = true)
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @CacheEvict(value = "transactions", allEntries = true)
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }
        transactionRepository.deleteById(id);
    }

    @CacheEvict(value = "transactions", allEntries = true)
    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + id + " not found"));

        transaction.setAmount(transactionDetails.getAmount());
        transaction.setType(transactionDetails.getType());
        transaction.setDescription(transactionDetails.getDescription());
        transaction.setTimestamp(transactionDetails.getTimestamp());

        return transactionRepository.save(transaction);
    }

    @Cacheable("transactions")
    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Cacheable(value = "transaction", key = "#id")
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + id + " not found"));
    }
}
