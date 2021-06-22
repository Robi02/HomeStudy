package com.de4bi.study.security.corespringsecurityboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.de4bi.study.security.corespringsecurityboard.domain.Resources;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long> {
 
    @Query("select r from Resources r join fetch r.roleSet where r.resourceType = 'url' order by r.orderNum desc")
    List<Resources> findAllResources();
}
