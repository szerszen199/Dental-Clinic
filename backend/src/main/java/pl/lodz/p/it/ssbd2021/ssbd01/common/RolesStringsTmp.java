package pl.lodz.p.it.ssbd2021.ssbd01.common;

public class RolesStringsTmp {
    public static final String user = "level.patient";
    public static final String admin = "level.administrator";
    public static final String receptionist = "level.receptionist";
    public static final String doctor = "level.doctor";

    // TODO: 10.05.2021 Takie cos lub inne dla wszystkich stringów?
    private RolesStringsTmp() {
    }

    public static String getPatient() {
        return user;
    }

    public static String getAdmin() {
        return admin;
    }

    public static String getReceptionist() {
        return receptionist;
    }

    public static String getDoctor() {
        return doctor;
    }
}
