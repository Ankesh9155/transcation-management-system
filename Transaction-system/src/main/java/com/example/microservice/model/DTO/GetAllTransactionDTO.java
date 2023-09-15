package com.example.microservice.model.DTO;

import com.example.microservice.model.TransactionType;
import com.example.microservice.model.serlizer.CustomDateTimeDeserializer;
import com.example.microservice.model.serlizer.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class GetAllTransactionDTO {

    private Long officeTransactionId;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private Date date;

    private String description;

    private TransactionType type;

    private Double amount;

    private Double runningBalance;
}
