package com.hazlin.transactionmanagement.mapper;

import com.hazlin.transactionmanagement.dto.TransactionDTO;
import com.hazlin.transactionmanagement.model.Transaction;

public class TransactionMapper {

    public static TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDescription(),
                transaction.getTimestamp()
        );
    }

    public static Transaction toEntity(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            return null;
        }
        return new Transaction(
                transactionDTO.getAmount(),
                transactionDTO.getType(),
                transactionDTO.getDescription(),
                transactionDTO.getTimestamp()
        );
    }
}
