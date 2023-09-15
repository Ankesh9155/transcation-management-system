package com.example.microservice.service;

import com.example.microservice.model.DTO.CreateOfficeTransactionDTO;
import com.example.microservice.model.DTO.GetAllTransactionDTO;
import com.example.microservice.model.TransactionType;
import com.example.microservice.model.entity.OfficeTransactionEntity;
import com.example.microservice.repository.OfficeTransactionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfficeTransactionService {

    private final OfficeTransactionRepo officeTransactionRepo;

    // Get all transactions
    public List<GetAllTransactionDTO> getAllTransactions() {
        log.info("Getting all transactions");
        List<OfficeTransactionEntity> transactionEntities = officeTransactionRepo.findAll(
                Sort.by(Sort.Direction.DESC, "date")
        );
        return transactionEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Create a new transaction
    public GetAllTransactionDTO createTransaction(@Valid CreateOfficeTransactionDTO createOfficeTransactionDTO) {
        log.info("Creating a new transaction: {}", createOfficeTransactionDTO);
        Double runningBalance = calculateRunningBalance(createOfficeTransactionDTO);

        OfficeTransactionEntity entity = mapToEntity(createOfficeTransactionDTO);
        entity.setRunningBalance(runningBalance); // Set the running balance in the entity

        OfficeTransactionEntity savedEntity = officeTransactionRepo.save(entity);

        return mapToDto(savedEntity);
    }

    private Double calculateRunningBalance(CreateOfficeTransactionDTO createOfficeTransactionDTO) {
        List<OfficeTransactionEntity> transactions = officeTransactionRepo.findAllByOrderByDateAsc();
        Double previousRunningBalance = CollectionUtils.isEmpty(transactions) ? 0.0 :
                transactions.get(transactions.size() - 1).getRunningBalance();

        Double amount = createOfficeTransactionDTO.getAmount();
        TransactionType type = createOfficeTransactionDTO.getType();
        if (type == TransactionType.Credit) {
            return previousRunningBalance + amount;
        } else if (type == TransactionType.Debit) {
            if (amount > previousRunningBalance) {
                throw new IllegalArgumentException("Debit amount cannot be greater than the running balance.");
            }
            return previousRunningBalance - amount;
        } else {
            // Handle invalid transaction type here
            throw new IllegalArgumentException("Invalid transaction type.");
        }
    }

    private GetAllTransactionDTO mapToDto(OfficeTransactionEntity officeTransactionEntity) {
        return GetAllTransactionDTO.builder()
                .officeTransactionId(officeTransactionEntity.getOfficeTransactionId())
                .date(officeTransactionEntity.getDate())
                .description(officeTransactionEntity.getDescription())
                .type(officeTransactionEntity.getType())
                .amount(officeTransactionEntity.getAmount())
                .runningBalance(officeTransactionEntity.getRunningBalance())
                .build();
    }

    private OfficeTransactionEntity mapToEntity(CreateOfficeTransactionDTO createOfficeTransactionDTO) {
        return OfficeTransactionEntity.builder()
                .date(createOfficeTransactionDTO.getDate())
                .description(createOfficeTransactionDTO.getDescription())
                .type(createOfficeTransactionDTO.getType())
                .amount(createOfficeTransactionDTO.getAmount())
                .build();
    }
}
