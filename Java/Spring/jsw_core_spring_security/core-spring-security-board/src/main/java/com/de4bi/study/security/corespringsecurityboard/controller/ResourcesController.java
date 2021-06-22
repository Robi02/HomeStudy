package com.de4bi.study.security.corespringsecurityboard.controller;

import java.util.HashSet;
import java.util.Set;

import com.de4bi.study.security.corespringsecurityboard.domain.Resources;
import com.de4bi.study.security.corespringsecurityboard.domain.Role;
import com.de4bi.study.security.corespringsecurityboard.repository.RoleRepository;
import com.de4bi.study.security.corespringsecurityboard.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.de4bi.study.security.corespringsecurityboard.security.service.ResourcesService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ResourcesController {

	private final ResourcesService resourcesService;
	private final RoleRepository roleRepository;
	private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
	
	@GetMapping(value="/admin/resources")
	public String createResources(ResourcesDto resourcesDto) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		Role role = roleRepository.findByRoleName(resourcesDto.getRoleName());
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		Resources resources = modelMapper.map(resourcesDto, Resources.class);
		resources.setRoleSet(roles);

		resourcesService.createResources(resources);
		urlFilterInvocationSecurityMetadataSource.reload();

		return "redirect:/admin/resources";
	}
}
