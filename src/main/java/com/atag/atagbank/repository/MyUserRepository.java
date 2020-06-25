package com.atag.atagbank.repository;

import com.atag.atagbank.model.MyUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyUserRepository extends PagingAndSortingRepository<MyUser, Long>{
    MyUser findByUsername(String username);
    MyUser findByName(String name);
    MyUser findByEmail(String email);
    List<MyUser> findByNameOrAccountOrAndAddressLike(String keyword);
}
