package com.vva.organizationservice.organizations;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @NotBlank(message = "Organization name cannot be null or empty")
    @Size(max = 100, message = "Organization name cannot be more than 100 characters")
    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Email cannot be null or empty")
    @Size(max = 45, message = "Email cannot be more than 45 characters")
    @Column(length = 45, nullable = false)
    private String email;

    @NotBlank(message = "poc cannot be null or empty")
    @Size(max = 45, message = "poc cannot be more than 45 characters")
    @Column(length = 45, nullable = false)
    private String poc;

    @NotBlank(message = "poc cannot be null or empty")
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    @Column(length = 10, nullable = false)
    private String phonenumber;

    @NotBlank(message = "webaddress cannot be null or empty")
    @Size(max = 500, message = "webaddress cannot be more than 500 characters")
    @Column(length = 500, nullable = false)
    private String webaddress;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    public Organization() {
    }

    public Organization(String id, String name, String email, String poc, String phonenumber, String webaddress, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.poc = poc;
        this.phonenumber = phonenumber;
        this.webaddress = webaddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // This makes the createdAt and updatedAt be the time when the row is made
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // This makes the updatedAt be the time when the row is updated
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public String getId() {
        return id;
    }

    public @NotBlank(message = "Organization name cannot be null or empty") @Size(max = 100, message = "Organization name cannot be more than 100 characters") String getName() {
        return name;
    }

    public @NotBlank(message = "Email cannot be null or empty") @Size(max = 45, message = "Email cannot be more than 45 characters") String getEmail() {
        return email;
    }

    public @NotBlank(message = "poc cannot be null or empty") @Size(max = 45, message = "poc cannot be more than 45 characters") String getPoc() {
        return poc;
    }

    public @NotBlank(message = "poc cannot be null or empty") @Size(min = 10, max = 10, message = "Phone number must be 10 characters") String getPhonenumber() {
        return phonenumber;
    }

    public @NotBlank(message = "webaddress cannot be null or empty") @Size(max = 500, message = "webaddress cannot be more than 500 characters") String getWebaddress() {
        return webaddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setName(@NotBlank(message = "Organization name cannot be null or empty") @Size(max = 100, message = "Organization name cannot be more than 100 characters") String name) {
        this.name = name;
    }

    public void setEmail(@NotBlank(message = "Email cannot be null or empty") @Size(max = 45, message = "Email cannot be more than 45 characters") String email) {
        this.email = email;
    }

    public void setPoc(@NotBlank(message = "poc cannot be null or empty") @Size(max = 45, message = "poc cannot be more than 45 characters") String poc) {
        this.poc = poc;
    }

    public void setPhonenumber(@NotBlank(message = "poc cannot be null or empty") @Size(min = 10, max = 10, message = "Phone number must be 10 characters") String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setWebaddress(@NotBlank(message = "webaddress cannot be null or empty") @Size(max = 500, message = "webaddress cannot be more than 500 characters") String webaddress) {
        this.webaddress = webaddress;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", poc='" + poc + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", webaddress='" + webaddress + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
