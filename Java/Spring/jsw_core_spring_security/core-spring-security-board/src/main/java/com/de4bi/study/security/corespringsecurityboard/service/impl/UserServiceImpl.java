package com.de4bi.study.security.corespringsecurityboard.service.impl;

import javax.transaction.Transactional;

import com.de4bi.study.security.corespringsecurityboard.domain.Account;
import com.de4bi.study.security.corespringsecurityboard.repository.UserRepository;
import com.de4bi.study.security.corespringsecurityboard.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(Account account) {
        userRepository.save(account);
    }
}
