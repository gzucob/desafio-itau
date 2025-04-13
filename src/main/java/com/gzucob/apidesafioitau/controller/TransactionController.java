package com.gzucob.apidesafioitau.controller;

import com.gzucob.apidesafioitau.dto.StatisticsResponse;
import com.gzucob.apidesafioitau.dto.TransactionRequest;
import com.gzucob.apidesafioitau.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/desafio-itau")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transacao")
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequest request) {
        transactionService.addTransaction(new TransactionRequest(request.valor(), request.dataHora()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<StatisticsResponse> getStatistics() {
        DoubleSummaryStatistics statistics = transactionService.getStatistics();
        return ResponseEntity.ok(new StatisticsResponse(statistics));
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> deleteTransaction() {
        transactionService.clearTransaction();
        return ResponseEntity.ok().build();
    }
}
