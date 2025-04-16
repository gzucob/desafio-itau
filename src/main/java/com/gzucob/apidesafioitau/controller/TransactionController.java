package com.gzucob.apidesafioitau.controller;

import com.gzucob.apidesafioitau.dto.StatisticsResponse;
import com.gzucob.apidesafioitau.dto.TransactionRequest;
import com.gzucob.apidesafioitau.services.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/desafio-itau")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transacao")
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        transactionRequest.validate();
        transactionService.addTransaction(new TransactionRequest(transactionRequest.valor(), transactionRequest.dataHora()));
        log.info("Transação registrada com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<StatisticsResponse> getStatistics(@RequestParam(value = "seconds", defaultValue = "60") Integer seconds) {
        if (seconds < 0) {
            log.warn("Segundo negativo, retornado valores dos utlimos 60s");
            seconds = 60; //Não trava a requisição, mas retorna o filtro padrão 60s
        }
        DoubleSummaryStatistics statistics = transactionService.getStatistics(seconds);
        log.info("Estatisticas geradas com sucesso!");
        return ResponseEntity.ok(new StatisticsResponse(statistics));
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> deleteTransaction() {
        transactionService.clearTransaction();
        log.info("Transação deletada com sucesso!");
        return ResponseEntity.ok().build();
    }
}
