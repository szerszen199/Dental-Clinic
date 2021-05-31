package pl.lodz.p.it.ssbd2021.ssbd01.auth.ejb.managers;


import pl.lodz.p.it.ssbd2021.ssbd01.entities.AuthViewEntity;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs Auth view menadżera encji.
 */
@Local
public interface AuthViewEntityManager {

    /**
     * Pobranie kont z widoku wraz z poziomami dostępu po loginie.
     *
     * @param login login
     * @return Lista kont
     * @throws AppBaseException app base exception
     */
    List<AuthViewEntity> findByLogin(String login) throws AppBaseException;
}
