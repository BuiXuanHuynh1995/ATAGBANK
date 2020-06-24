package com.atag.atagbank.service.user;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.model.Role;
import com.atag.atagbank.repository.MyUserRepository;
import com.atag.atagbank.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyUserServiceImpl implements MyUserService, UserDetailsService {
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
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRole(userRole);
        return myUserRepository.save(user);
    }

    @Override
    public List<MyUser> findAllList() {
        return (List<MyUser>) myUserRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username);
        if (myUser == null) {
            myUser = new MyUser();
            myUser.setUsername(username);
            myUser.setPassword("");
            myUser.setRole(new Role(2L,"ROLE_USER"));
        }
        List<GrantedAuthority> authors = new ArrayList<>();
        authors.add(new SimpleGrantedAuthority(myUser.getRole().getRole()));

        return new User(myUser.getUsername(), myUser.getPassword(), authors);
    }

}
