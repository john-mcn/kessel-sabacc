package com.johnm.sabacc.server.domain.game.components;

import com.johnm.sabacc.server.util.EnumUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ShiftTokenEntity {
    @Id
    private String name;

    private String description;

    public ShiftTokenEntity() {}

    public ShiftTokenEntity(String name) { this.name = name; }

    public ShiftTokenEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static ShiftTokenEntity createFromEnum(ShiftToken token) {
        return new ShiftTokenEntity(EnumUtils.capitaliseEachWordFromEnum(token.name()), token.getDescription());
    }

    public ShiftToken toEnum() { //TODO any use?
        return ShiftToken.valueOf(EnumUtils.sanitiseString(this.name));
    }
    public static ShiftToken toEnum(String tokenName) {
        return ShiftToken.valueOf(EnumUtils.sanitiseString(tokenName));
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
