package com.de4bi.study.security.corespringsecurityboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.de4bi.study.security.corespringsecurityboard.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByRoleName(String roleName);
}
