package com.atag.atagbank.service.user;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.model.Role;
import com.atag.atagbank.repository.MyUserRepository;
import com.atag.atagbank.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserServiceImpl implements MyUserService {
//    @Autowired
//    MyUserRepository myUserRepository;

    private MyUserRepository myUserRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MyUserServiceImpl(MyUserRepository myUserRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.myUserRepository = myUserRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Page<MyUser> findAll(Pageable pageable) {
        return myUserRepository.findAll(pageable);
    }

    @Override
    public void save(MyUser user) {
        myUserRepository.save(user);
    }

    @Override
    public MyUser findById(Long id) {
        return myUserRepository.findById(id).get();
    }

    @Override
    public MyUser findByUserName(String username) {
        return myUserRepository.findByUsername(username);
    }

    @Override
    public MyUser findByName(String name) {
        return myUserRepository.findByName(name);
    }

    @Override
    public MyUser findByEmail(String email) {
        return myUserRepository.findByEmail(email);
    }

    @Override
    public MyUser saveUser(MyUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(bCryptPasswordEncoder.encode(user.getConfirmPassword()));
        Role userRole = roleRepository.findByRole("ROLE_ADMIN");
        user.setRole(userRole);
        return myUserRepository.save(user);
    }

    @Override
    public boolean isRegister(MyUser user) {
        boolean isRegister = false;
        Iterable<MyUser> users = myUserRepository.findAll();
        for (MyUser currentUser : users) {
            if (user.getUsername().equals(currentUser.getUsername()) ||
                    user.getEmail().equals(currentUser.getEmail())) {
                isRegister = true;
                break;
            }
        }
        return isRegister;
    }

    @Override
    public boolean isCorrectConfirmPassword(MyUser user) {
        boolean isCorrentConfirmPassword = false;
        if (user.getPassword().equals(user.getConfirmPassword())) {
            isCorrentConfirmPassword = true;
        }
        return isCorrentConfirmPassword;
    }

    @Override
      public List<MyUser> findAllList() {
        return (List<MyUser>) myUserRepository.findAll();
    }
}
