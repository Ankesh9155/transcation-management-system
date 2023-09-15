package com.example.microservice.model.DTO;

import com.example.microservice.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class CreateOfficeTransactionDTO {

    private Date date;

    private String description;

    private TransactionType type;

    private Double amount;

}

