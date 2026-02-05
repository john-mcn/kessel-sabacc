package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.dto.SyndicateDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Syndicate {
    @Id
    private String name;

    private String displayName;
    private String description;

    public Syndicate() {}

    public Syndicate(String name, String displayName, String description) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public SyndicateDTO toDTO() {
        SyndicateDTO dto = new SyndicateDTO(
                name,
                displayName,
                description
        );
        return dto;
    }
}
