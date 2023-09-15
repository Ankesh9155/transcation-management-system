package com.example.microservice.repository;

import com.example.microservice.model.entity.OfficeTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeTransactionRepo extends JpaRepository<OfficeTransactionEntity,Long> {
    List<OfficeTransactionEntity> findAllByOrderByDateAsc();;
}
