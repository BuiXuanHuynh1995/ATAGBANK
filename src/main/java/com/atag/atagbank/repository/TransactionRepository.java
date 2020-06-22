package com.atag.atagbank.repository;

import com.atag.atagbank.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository <Transaction,Long>{
}
