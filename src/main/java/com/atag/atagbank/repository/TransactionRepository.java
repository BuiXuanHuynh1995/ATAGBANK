package com.atag.atagbank.repository;

import com.atag.atagbank.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository <Transaction,Long>{
}
