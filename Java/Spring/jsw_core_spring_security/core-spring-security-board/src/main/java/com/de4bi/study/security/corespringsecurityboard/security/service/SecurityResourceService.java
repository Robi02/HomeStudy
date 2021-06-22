package com.de4bi.study.security.corespringsecurityboard.security.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.de4bi.study.security.corespringsecurityboard.domain.Resources;
import com.de4bi.study.security.corespringsecurityboard.repository.ResourcesRepository;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SecurityResourceService {
    
    private final ResourcesRepository resourcesRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllResources();

        resourcesList.forEach(re -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            re.getRoleSet().forEach(role -> {
                configAttributeList.add(new SecurityConfig(role.getRoleName()));
                result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList);
            });
        });

        return result;
    }
}
