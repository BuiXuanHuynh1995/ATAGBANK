package com.atag.atagbank.repositoty;

import com.atag.atagbank.model.MyUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MyUserRepository extends PagingAndSortingRepository<MyUser, Long> {
}
