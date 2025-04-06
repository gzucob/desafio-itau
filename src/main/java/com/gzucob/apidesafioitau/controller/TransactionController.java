package com.gzucob.apidesafioitau.controller;

import com.gzucob.apidesafioitau.dto.TransactionRequest;
import com.gzucob.apidesafioitau.model.TransactionModel;
import com.gzucob.apidesafioitau.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequest request) {
        if (request.getDataHora().isAfter(OffsetDateTime.now())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        transactionService.addTransaction(new TransactionModel(request.getValor(), request.getDataHora()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
