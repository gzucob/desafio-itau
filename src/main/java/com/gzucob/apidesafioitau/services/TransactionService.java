package com.gzucob.apidesafioitau.services;

import com.gzucob.apidesafioitau.dto.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Slf4j
public class TransactionService {

    private final Queue<TransactionRequest> transaction = new ConcurrentLinkedQueue<>();

    public void addTransaction(TransactionRequest transactionRequest) {
        if (transactionRequest.valor() == null || transactionRequest.dataHora() == null) {
            log.error("O json não pode ser nulo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (transactionRequest.valor().compareTo(BigDecimal.ZERO) < 0) {
            log.error("Valor não pode ser negativo!");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (transactionRequest.dataHora().isAfter(OffsetDateTime.now())) {
            log.error("DataHora não pode ser maior que dataHora atual");
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
        if (transaction.isEmpty()) {
            log.warn("Nada há ser deletado!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            transaction.clear();
        }
    }
}
