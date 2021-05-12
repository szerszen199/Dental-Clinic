package pl.lodz.p.it.ssbd2021.ssbd01.common;

/**
 * Reprezentuje możliwe poziomy dostępu.
 */
public enum Levels {
    PATIENT(RolesStringsTmp.getPatient()),
    RECEPTIONIST(RolesStringsTmp.getReceptionist()),
    DOCTOR(RolesStringsTmp.getDoctor()),
    ADMINISTRATOR(RolesStringsTmp.getAdmin());


    private final String level;


    public String getLevel() {
        return level;
    }

    Levels(String level) {
        this.level = level;
    }
}
