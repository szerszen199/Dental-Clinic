package pl.lodz.p.it.ssbd2021.ssbd01.common;

import java.util.Map;

/**
 * Reprezentuje możliwe poziomy dostępu.
 */
public enum Levels {
    PATIENT("level.patient"),
    RECEPTIONIST("level.receptionist"),
    DOCTOR("level.doctor"),
    ADMINISTRATOR("level.administrator");

    private final String level;

    public String getLevel() {
        return level;
    }

    Levels(String level) {
        this.level = level;
    }
}
