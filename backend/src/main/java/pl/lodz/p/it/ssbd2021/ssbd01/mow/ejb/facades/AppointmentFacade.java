package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Fasada wizyt.
 */
@Stateless
@PermitAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LogInterceptor.class)
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

    public List<Appointment> findFutureUnassignedAppointments() throws AppBaseException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Appointment> cq = em.getCriteriaBuilder().createQuery(Appointment.class);
            Root<Appointment> root = cq.from(Appointment.class);

            cq.select(root).where(cb.and(cb.isNull(root.get("patient")), cb.greaterThan(root.<LocalDateTime>get("appointmentDate"), LocalDateTime.now())));


            return em.createQuery(cq).getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }


}
