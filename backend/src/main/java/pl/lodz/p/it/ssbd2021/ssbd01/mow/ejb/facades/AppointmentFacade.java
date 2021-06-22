package pl.lodz.p.it.ssbd2021.ssbd01.mow.ejb.facades;

import pl.lodz.p.it.ssbd2021.ssbd01.common.AbstractFacade;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.Appointment;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.mow.AppointmentException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.LogInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return em;
    }

    /**
     * Zwraca listę wolnych przyszłych terminów wizyt u wszystkich lekarzy.
     *
     * @return lista wolnych przyszłych terminów wizyt u wszystkich lekarzy.
     * @throws AppBaseException wyjątek.
     */
    public List<Appointment> findAllFutureUnassignedAppointmentsSlots() throws AppBaseException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Appointment> cq = em.getCriteriaBuilder().createQuery(Appointment.class);
            Root<Appointment> root = cq.from(Appointment.class);

            cq.select(root).where(cb.and(cb.isNull(root.get("patient")), cb.greaterThan(root.get("appointmentDate"), LocalDateTime.now())));


            return em.createQuery(cq).getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Metoda zwracająca wszystkie umówione wizyty.
     *
     * @return lista wizyt
     * @throws AppBaseException app base exception
     */
    public List<Appointment> findAllScheduledAppointments() throws AppBaseException {
        try {
            TypedQuery<Appointment> tq = em.createNamedQuery("Appointment.findAllScheduled", Appointment.class);
            return tq.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Metoda zwracająca wszystkie umówione wizyty dla doktora o zadanym loginie.
     *
     * @param doctor zalogowany lekarz
     * @return lista wizyt
     * @throws AppBaseException app base exception
     */
    public List<Appointment> findAllScheduledAppointmentsByDoctor(Account doctor) throws AppBaseException {
        try {
            TypedQuery<Appointment> tq = em.createNamedQuery("Appointment.findAllScheduledByDoctor", Appointment.class);
            tq.setParameter("doctor", doctor);
            return tq.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Metoda zwracająca wszystkie umówione wizyty dla pacjenta o zadanym loginie.
     *
     * @param patient zalogowany pacjent
     * @return lista wizyt
     * @throws AppBaseException app base exception
     */
    public List<Appointment> findAllScheduledAppointmentsByPatient(Account patient) throws AppBaseException {
        try {
            TypedQuery<Appointment> tq = em.createNamedQuery("Appointment.findAllScheduledByPatient", Appointment.class);
            tq.setParameter("patient", patient);
            return tq.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }
    }

    /**
     * Zwraca listę wolnych przyszłych terminów wizyt u danego lekarza.
     *
     * @param doctorId lekarz którego terminy mają zostać zwrócone.
     * @return lista dostępnych terminów.
     * @throws AppBaseException wyjątek.
     */
    public List<Appointment> findFutureUnassignedAppointmentSlotsForDoctor(Long doctorId) throws AppBaseException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Appointment> cq = em.getCriteriaBuilder().createQuery(Appointment.class);
            Root<Appointment> root = cq.from(Appointment.class);

            cq.select(root).where(cb.and(cb.isNull(root.get("patient")), cb.greaterThan(root.get("appointmentDate"), LocalDateTime.now())), cb.equal(root.get("doctor"), doctorId));


            return em.createQuery(cq).getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseError(e);
        }

    }

    /**
     *  Metoda do aktualizacji oceny.
     * @param id id wizyty do aktualizacji
     * @param decimal ocena
     * @throws AppointmentException błąd edycji.
     */
    public void updateRating(Long id, BigDecimal decimal) throws AppointmentException {
        try {
            Query query = em.createNamedQuery("Appointment.updateRating");
            query.setParameter("id", id);
            query.setParameter("rating", decimal);
            query.executeUpdate();
        } catch (IllegalStateException | PersistenceException e) {
            throw AppointmentException.appointmentEditFailed();
        }
    }


}
