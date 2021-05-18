package pl.lodz.p.it.ssbd2021.ssbd01.common;

import pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.AdminData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.DoctorData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.ReceptionistData;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mok.AccessLevelException;

/**
 * Klasa mapująca nazwę poziomu dostępu na obiekt.
 */
public class AccessLevelMapper {

    /**
     * Metoda przekształca nazwę poziomu dostępu na jego obiekt.
     *
     * @param level poziom dostępu do zamiany na obiekt
     * @return obiekt poziomu dostępu
     * @throws AccessLevelException wyjątek gdy nie ma taktiego poziomu dostępu
     */
    public static AccessLevel mapLevelNameToAccessLevel(String level) throws AccessLevelException {
        if (I18n.ADMIN.equals(level)) {
            return new AdminData();
        } else if (I18n.DOCTOR.equals(level)) {
            return new DoctorData();
        } else if (I18n.RECEPTIONIST.equals(level)) {
            return new ReceptionistData();
        } else if (I18n.PATIENT.equals(level)) {
            return new PatientData();
        }
        throw AccessLevelException.noSuchAccessLevel();
    }


}
