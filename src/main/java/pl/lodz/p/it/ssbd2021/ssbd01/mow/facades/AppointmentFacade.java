package pl.lodz.p.it.ssbd2021.ssbd01.mow.facades;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;

/**
 * Typ Appointment facade.
 */
public class AppointmentFacade extends AbstractFacade<Appointment> {

    @PersistenceContext(unitName = "ssbd01mowPU")
    private EntityManager em;

    /**
     * Tworzy nową instancję klasy AppointmentFacade.
     */
    public AppointmentFacade() {
        super(Appointment.class);
    }

    /**
     * Tworzy nową instancję klasy AppointmentFacade.
     *
     * @param entityClass typ obiektowy encji
     */
    public AppointmentFacade(Class<Appointment> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
