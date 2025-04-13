package com.gzucob.apidesafioitau.services;

import com.gzucob.apidesafioitau.dto.TransactionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransactionService {

    private final Queue<TransactionRequest> transaction = new ConcurrentLinkedQueue<>();

    public void addTransaction(TransactionRequest transactionRequest) {
        if (transactionRequest.valor() == null || transactionRequest.dataHora() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (transactionRequest.valor().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (transactionRequest.dataHora().isAfter(OffsetDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        transaction.add(transactionRequest);
    }

    public DoubleSummaryStatistics getStatistics() {
        OffsetDateTime dataHoraNow = OffsetDateTime.now();
        OffsetDateTime limit = dataHoraNow.minusSeconds(60);
        return transaction.stream()
                .filter(t -> t.dataHora().isAfter(limit))
                .map(TransactionRequest::valor)
                .mapToDouble(BigDecimal::doubleValue) //Sei que não seria o ideal fazer essa conversão
                .summaryStatistics();
    }

    public void clearTransaction() {
        transaction.clear();
    }
}
