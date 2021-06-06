package com.de4bi.study.security.corespringsecurityboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.de4bi.study.security.corespringsecurityboard.domain.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {
 
    public Account findByUsername(String username);
}
