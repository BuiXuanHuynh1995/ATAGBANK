package com.atag.atagbank.repository;

import com.atag.atagbank.model.MyUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends PagingAndSortingRepository<MyUser, Long>{

}
