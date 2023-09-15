package com.example.microservice.model.entity;

import com.example.microservice.model.TransactionType;
import com.example.microservice.model.serlizer.CustomDateTimeDeserializer;
import com.example.microservice.model.serlizer.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name="Office_transaction")
@AllArgsConstructor
@NoArgsConstructor

public class OfficeTransactionEntity extends Auditable{

    @Id
    @Column(name = "office_transaction_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long officeTransactionId;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "running_balance", nullable = false)
    private Double runningBalance;
}
