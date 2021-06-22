package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import pl.lodz.p.it.ssbd2021.ssbd01.utils.Encryptor;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

/**
 * Typ Prescription - klasa encyjna dal recept.
 */
@Entity
@Table(name = "prescriptions")
@NamedQueries({
        @NamedQuery(name = "Prescription.findAll", query = "SELECT p FROM Prescription p"),
        @NamedQuery(name = "Prescription.findById", query = "SELECT p FROM Prescription p WHERE p.id = :id"),
        @NamedQuery(name = "Prescription.findByExpiration", query = "SELECT p FROM Prescription p WHERE p.expiration = :expiration"),
        @NamedQuery(name = "Prescription.findByMedications", query = "SELECT p FROM Prescription p WHERE p.medications = :medications"),
        @NamedQuery(name = "Prescription.findByVersion", query = "SELECT p FROM Prescription p WHERE p.version = :version"),
        @NamedQuery(name = "Prescription.findByCreationDateTime", query = "SELECT p FROM Prescription p WHERE p.creationDateTime = :creationDateTime"),
        @NamedQuery(name = "Prescription.findByModificationDateTime", query = "SELECT p FROM Prescription p WHERE p.modificationDateTime = :modificationDateTime")})
public class Prescription extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescriptions_generator")
    @SequenceGenerator(name = "prescriptions_generator", sequenceName = "prescriptions_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", updatable = false, nullable = false)
    @NotNull
    private Long id;


    @Basic(optional = false)
    @Column(name = "expiration", updatable = false, nullable = false)
    @NotNull
    private LocalDateTime expiration;

    @Column(name = "medications")
    private byte[] medications;

    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    @NotNull
    private Account doctor;
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    @NotNull
    private Account patient;

    /**
     * Tworzy nową instancję klasy Prescription.
     */
    public Prescription() {
    }

    /**
     * Tworzy nową instancję klasy Prescription.
     *
     * @param medications      przepisane leki
     * @param propertiesLoader properties loader
     * @throws NoSuchPaddingException    nie istniejący padding dla dekodowania
     * @throws IllegalBlockSizeException błędny rozmiar bloku dla dekodowania
     * @throws NoSuchAlgorithmException  błędny algorytm dla dekodowania
     * @throws BadPaddingException       błędny padding dla dekodowania
     * @throws InvalidKeyException       błędny klucz do dekodowania
     */
    public Prescription(String medications, PropertiesLoader propertiesLoader)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Encryptor encryptor = new Encryptor(propertiesLoader);
        this.medications = encryptor.encryptMessage(medications);
    }

    @Override
    public Long getId() {
        return id;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public byte[] getMedications() {
        return medications;
    }


    /**
     * Pobiera pole medications decrypted.
     *
     * @param propertiesLoader properties loader
     * @return medications decrypted
     * @throws NoSuchPaddingException    nie istniejący padding dla dekodowania
     * @throws IllegalBlockSizeException błędny rozmiar bloku dla dekodowania
     * @throws NoSuchAlgorithmException  błędny algorytm dla dekodowania
     * @throws BadPaddingException       błędny padding dla dekodowania
     * @throws InvalidKeyException       błędny klucz do dekodowania
     */
    public String getMedicationsDecrypted(PropertiesLoader propertiesLoader)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Encryptor encryptor = new Encryptor(propertiesLoader);
        return encryptor.decryptMessage(this.medications);
    }

    public void setMedications(byte[] medications) {
        this.medications = medications;
    }

    /**
     * Ustawia pole medications.
     *
     * @param medications      medications
     * @param propertiesLoader properties loader
     * @throws NoSuchPaddingException    nie istniejący padding dla dekodowania
     * @throws IllegalBlockSizeException błędny rozmiar bloku dla dekodowania
     * @throws NoSuchAlgorithmException  błędny algorytm dla dekodowania
     * @throws BadPaddingException       błędny padding dla dekodowania
     * @throws InvalidKeyException       błędny klucz do dekodowania
     */
    public void setMedications(String medications, PropertiesLoader propertiesLoader)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Encryptor encryptor = new Encryptor(propertiesLoader);
        this.medications = encryptor.encryptMessage(medications);
    }

    public Account getDoctor() {
        return doctor;
    }

    public void setDoctor(Account doctor) {
        this.doctor = doctor;
    }

    public Account getPatient() {
        return patient;
    }

    public void setPatient(Account patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.Prescription[ id=" + id + " ]";
    }

}