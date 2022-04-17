package org.lukaszse.carRental.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SecurityRole {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_GUEST("GUEST");

    private final String shortName;

    public String getFullName() {
        return "ROLE_%s".formatted(shortName);
    }
}
