package com.atag.atagbank.service.user;

import com.atag.atagbank.model.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MyUserService {
    Page<MyUser> findAll(Pageable pageable);

    void save(MyUser user);

    Optional<MyUser> findById(Long id);
}
