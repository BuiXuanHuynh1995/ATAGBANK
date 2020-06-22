package com.atag.atagbank.service.user;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.repositoty.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserServiceImpl implements MyUserService {
    @Autowired
    MyUserRepository myUserRepository;

    @Override
    public Page<MyUser> findAll(Pageable pageable) {
        return myUserRepository.findAll(pageable);
    }

    @Override
    public void save(MyUser user) {
        myUserRepository.save(user);

    }

    @Override
    public Optional<MyUser> findById(Long id) {
        return myUserRepository.findById(id);
    }
}
