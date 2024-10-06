package com.vva.organizationservice.organizations;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public List<Organization> getOrganizations() {
        return this.organizationService.getOrganizations();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public void createNewOrganization(@Valid @RequestBody Organization newOrganization) {
        this.organizationService.createNewOrganization(newOrganization);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(path = "{organizationId}")
    public void updateOrganization(
            @PathVariable("organizationId") String organizationId,
            @Valid @RequestBody Organization updatedOrganization
    ) {
        System.out.println(updatedOrganization);
        this.organizationService.updateOrganization(organizationId, updatedOrganization);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(path = "{organizationId}")
    public void deleteOrganization(@PathVariable("organizationId") String organizationId) {
        this.organizationService.deleteOrganization(organizationId);
    }
}
