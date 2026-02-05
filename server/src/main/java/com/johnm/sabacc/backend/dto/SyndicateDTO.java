package com.johnm.sabacc.backend.dto;

import com.johnm.sabacc.backend.domain.Syndicate;

public class SyndicateDTO {
    private String name;
    private String displayName;
    private String description;

    public SyndicateDTO() {}

    public SyndicateDTO(String name, String displayName, String description) {
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

    public Syndicate toEntity() {
        Syndicate syndicate = new Syndicate(
                name,
                displayName,
                description
        );
        return syndicate;
    }
}