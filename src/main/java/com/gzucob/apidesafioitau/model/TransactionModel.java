package com.gzucob.apidesafioitau.model;

import java.time.OffsetDateTime;

public class TransactionModel {

    private Double valor;
    private OffsetDateTime dataHora;

    public TransactionModel(final Double valor, final OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Double getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }
}
