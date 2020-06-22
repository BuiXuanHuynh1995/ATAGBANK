package com.atag.atagbank.service.transaction;

import com.atag.atagbank.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITransactionService {
    Page<Transaction> findAll(Pageable pageable);
}
