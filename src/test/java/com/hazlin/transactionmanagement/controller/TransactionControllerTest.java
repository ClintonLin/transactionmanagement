package com.hazlin.transactionmanagement.controller;

import com.hazlin.transactionmanagement.dto.TransactionDTO;
import com.hazlin.transactionmanagement.exception.TransactionNotFoundException;
import com.hazlin.transactionmanagement.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        transactionDTO = new TransactionDTO(1L, 100.0, "CREDIT", "Test transaction", LocalDateTime.now());
    }

    @Test
    void createTransaction_shouldReturnCreated() throws Exception {
        given(transactionService.createTransaction(any())).willReturn(null);

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllTransactions_shouldReturnOk() throws Exception {
        Page<TransactionDTO> page = new PageImpl<>(Collections.singletonList(transactionDTO));
        given(transactionService.getAllTransactions(any(PageRequest.class))).willReturn(null);

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk());
    }

    @Test
    void getTransactionById_shouldReturnOk() throws Exception {
        given(transactionService.getTransactionById(1L)).willReturn(null);

        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getTransactionById_shouldReturnNotFound() throws Exception {
        given(transactionService.getTransactionById(1L)).willThrow(new TransactionNotFoundException("Transaction not found"));

        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTransaction_shouldReturnOk() throws Exception {
        given(transactionService.updateTransaction(eq(1L), any())).willReturn(null);

        mockMvc.perform(put("/api/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updateTransaction_shouldReturnNotFound() throws Exception {
        given(transactionService.updateTransaction(eq(1L), any())).willThrow(new TransactionNotFoundException("Transaction not found"));

        mockMvc.perform(put("/api/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTransaction_shouldReturnNoContent() throws Exception {
        doNothing().when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTransaction_shouldReturnNotFound() throws Exception {
        doThrow(new TransactionNotFoundException("Transaction not found")).when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isNotFound());
    }
}
