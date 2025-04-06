package com.gzucob.apidesafioitau.services;

import com.gzucob.apidesafioitau.model.TransactionModel;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransactionService {

    private final Queue<TransactionModel> transaction = new ConcurrentLinkedQueue<>();

    public void addTransaction(TransactionModel transactionModel) {
        transaction.add(transactionModel);
    }

    public void clearTransaction() {
        transaction.clear();
    }

    public DoubleSummaryStatistics getStatistics() {
        OffsetDateTime dataHoraNow = OffsetDateTime.now();
        return transaction.stream()
                .filter(t -> t.getDataHora().isAfter(dataHoraNow.minusSeconds(60)))
                .mapToDouble(TransactionModel::getValor)
                .summaryStatistics();
    }
}
