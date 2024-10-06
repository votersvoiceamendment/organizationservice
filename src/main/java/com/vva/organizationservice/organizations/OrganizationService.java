package com.vva.organizationservice.organizations;

import com.vva.organizationservice.utils.UpdateUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<Organization> getOrganizations() {
        return this.organizationRepository.findAll();
    }

    public void createNewOrganization(@Valid Organization newOrganization) {
        this.organizationRepository.save(newOrganization);
    }

    @Transactional
    public void updateOrganization(
            String organizationId,
            @Valid Organization updatedOrganization
    ) {
        Organization organization = this.organizationRepository.findById(organizationId).orElseThrow(() -> {
            return new IllegalStateException("Organization with the id "+organizationId+" does not exist.");
        });

        // Change the values for the post
        // This uses a utils function that handles checking if a null values
        // was sent in the request and if a change has occurred
        UpdateUtils.updateFieldIfChanged(organization::getName, organization::setName, updatedOrganization.getName());
        UpdateUtils.updateFieldIfChanged(organization::getEmail, organization::setEmail, updatedOrganization.getEmail());
        UpdateUtils.updateFieldIfChanged(organization::getPoc, organization::setPoc, updatedOrganization.getPoc());
        UpdateUtils.updateFieldIfChanged(organization::getPhonenumber, organization::setPhonenumber, updatedOrganization.getPhonenumber());
        UpdateUtils.updateFieldIfChanged(organization::getWebaddress, organization::setWebaddress, updatedOrganization.getWebaddress());
    }


    public void deleteOrganization(String organizationId) {
        boolean exists = this.organizationRepository.existsById(organizationId);
        if (!exists) {
            throw new IllegalStateException("A organization with the id "+organizationId+" does not exist");
        }

        this.organizationRepository.deleteById(organizationId);
    }
}
