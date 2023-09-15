package com.example.microservice.controller;

import com.example.microservice.model.DTO.CreateOfficeTransactionDTO;
import com.example.microservice.model.DTO.GetAllTransactionDTO;
import com.example.microservice.service.OfficeTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Controller

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(path = "/transaction",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OfficeTransactionController {

    private final @NotNull OfficeTransactionService officeTransactionService;

    //GET Request
    @GetMapping(path = "/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetAllTransactionDTO>> getAllTransaction() {
        log.info("Processing started for getJTransaction");
        try {
            return ResponseEntity.ok(officeTransactionService.getAllTransactions());
        } finally {
            log.info("Processing completed for Transaction");
        }
    }

    //POST Request
    @PostMapping(path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAllTransactionDTO> createTransaction(@Valid @RequestBody CreateOfficeTransactionDTO
                                                                              createOfficeTransactionDTO) {
        log.info("Processing started for createTransaction");
        try {
            log.info("Request Body for saveTransaction: {}", createOfficeTransactionDTO);
            return ResponseEntity.ok(officeTransactionService.createTransaction(createOfficeTransactionDTO));
        } finally {
            log.info("Processing completed for Transaction");
        }
    }

}

