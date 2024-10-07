package com.vva.organizationservice.organizations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vva.organizationservice.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrganizationController.class)
@EnableMethodSecurity
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationService organizationService;

    // Test GET requests (No JWT needed)
    @Test
    // TODO!! This is a bad test!! it should NOT have the MockUser for a GET!!
//    @WithMockUser()
    void canGetOrganizations() throws Exception {
        mockMvc.perform(get("/api/v1/organizations"))
                .andExpect(status().isOk());
    }

    // Test POST requests (JWT needed)
    @Test
    @WithMockUser(roles = "ADMIN")
    void canCreateNewOrganization() throws Exception {
        Organization newOrganization = new Organization();

        mockMvc.perform(post("/api/v1/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newOrganization)))
                .andExpect(status().isForbidden());  // Assuming you need JWT, this should return Forbidden for unauthorized
    }

    @Test
    void canUpdateOrganizationNoJwtReturns401() throws Exception {
        // Create a fully valid organization object to avoid validation errors
        Organization updatedOrganization = new Organization();
        updatedOrganization.setName("Valid Organization");
        updatedOrganization.setPoc("Valid Point of Contact");
        updatedOrganization.setEmail("validemail@example.com");
        updatedOrganization.setPhonenumber("1234567890");
        updatedOrganization.setWebaddress("https://example.com");

        // Perform the request without a valid JWT
        mockMvc.perform(put("/api/v1/organizations/{id}", "orgId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedOrganization))
                        .with(csrf()))  // CSRF is mocked
                .andExpect(status().isUnauthorized());  // Expect 403 Forbidden due to missing JWT
    }


    // Test DELETE requests (JWT needed)
    @Test
    @WithMockUser(roles = "ADMIN")
    void canDeleteOrganization() throws Exception {
        mockMvc.perform(delete("/api/v1/organizations/{id}", "orgId")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    // Test 404 Not Found when trying to delete a non-existent organization
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteNonExistentOrganizationReturns404() throws Exception {
        // Mock OrganizationService to throw exception for non-existent org
        doThrow(new IllegalStateException("Organization not found"))
                .when(organizationService).deleteOrganization("nonExistentOrgId");

        mockMvc.perform(delete("/api/v1/organizations/{id}", "nonExistentOrgId")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }


    // Test 403 Forbidden cause role isn't allowed
    @Test
    @WithMockUser(roles = "USER")  // Simulate a user without admin privileges
    void deleteOrganizationRoleNotAllowedReturns403() throws Exception {
        // Do not mock the service to throw an exception here since we're testing access control
        // Focus only on the RBAC part
        mockMvc.perform(delete("/api/v1/organizations/{id}", "nonExistentOrgId")
                        .with(csrf()))  // Mock CSRF token
                .andExpect(status().isForbidden());  // Expect 403 Forbidden due to insufficient role
    }

    // Test 400 Bad Request for Invalid Data (POST)
    @Test
    @WithMockUser(roles = "ADMIN")
    void createOrganizationWithInvalidDataReturns400() throws Exception {
        Organization invalidOrganization = new Organization();  // Missing organization name

        mockMvc.perform(post("/api/v1/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidOrganization))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")  // Simulate an admin user
    void deleteOrganizationWithInvalidIdReturns400() throws Exception {
        // Mock the service to throw IllegalArgumentException for invalid ID
        doThrow(new IllegalArgumentException("Invalid organization ID. Expected UUID."))
                .when(organizationService).deleteOrganization("invalidId");

        mockMvc.perform(delete("/api/v1/organizations/{id}", "invalidId")
                        .with(csrf()))  // Mock CSRF token
                .andExpect(status().isBadRequest())  // Expect 400 Bad Request for invalid ID format
                .andExpect(content().string("Invalid organization ID. Expected UUID."));
    }
}
