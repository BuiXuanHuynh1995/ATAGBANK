package com.atag.atagbank.repository;

import com.atag.atagbank.model.MyUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MyUserRepository extends PagingAndSortingRepository<MyUser, Long> {
}
