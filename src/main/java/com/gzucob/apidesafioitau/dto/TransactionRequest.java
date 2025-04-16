package com.gzucob.apidesafioitau.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Slf4j
public record TransactionRequest(BigDecimal valor, OffsetDateTime dataHora) {
    public void validate() {
        if (valor() == null || dataHora() == null) {
            log.error("Campos valor/data/hora não pode ser vazio");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (valor().compareTo(BigDecimal.ZERO) < 0) {
            log.error("Valor da transação não pode ser negativo!");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (dataHora().isAfter(OffsetDateTime.now())) {
            log.error("Data/hora não pode ser maior que data/Hora atual");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

