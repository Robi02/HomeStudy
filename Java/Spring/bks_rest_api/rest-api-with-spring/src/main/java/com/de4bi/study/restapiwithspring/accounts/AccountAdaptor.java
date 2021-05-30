package com.de4bi.study.restapiwithspring.accounts;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AccountAdaptor extends User {
    
    private Account account;

    public AccountAdaptor(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("EventResource" + r.name()))
                .collect(Collectors.toSet());
    }
}
