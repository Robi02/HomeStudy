package com.de4bi.study.security.corespringsecurityboard.security.service;

import com.de4bi.study.security.corespringsecurityboard.domain.Resources;
import com.de4bi.study.security.corespringsecurityboard.repository.ResourcesRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ResourcesService {
    
    private final ResourcesRepository resourcesRepository;

    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
    }
}
