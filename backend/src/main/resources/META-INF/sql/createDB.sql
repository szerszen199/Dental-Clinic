DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS documentation_entries;
DROP TABLE IF EXISTS medical_documentations;
DROP TABLE IF EXISTS appointments;
DROP VIEW IF EXISTS glassfish_auth_view;
DROP TABLE IF EXISTS access_levels;
DROP TABLE IF EXISTS doctors_ratings;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS doctors_ratings;
DROP TABLE IF EXISTS accounts;


DROP SEQUENCE IF EXISTS accounts_seq;
DROP SEQUENCE IF EXISTS access_levels_seq;
DROP SEQUENCE IF EXISTS appointments_seq;
DROP SEQUENCE IF EXISTS medical_documentations_seq;
DROP SEQUENCE IF EXISTS documentation_entries_seq;
DROP SEQUENCE IF EXISTS prescriptions_seq;
DROP SEQUENCE IF EXISTS doctors_ratings_seq;

-- Tabela reprezentująca dane użytkownika
CREATE TABLE accounts
(
    id                                        BIGINT PRIMARY KEY,          -- klucz główny tabeli
    login                                     VARCHAR(60)        NOT NULL  -- login użytkownika, niezmienny
        CONSTRAINT acc_login_unique UNIQUE
        constraint account_login_not_anon check (login not in ('anonymous')),
    email                                     VARCHAR(100)       NOT NULL  -- Adres email użytkownika, wykorzystywany do wysłania wiadomości z linkiem weryfikacyjnym
        CONSTRAINT acc_email_unique UNIQUE,
    password                                  CHAR(64)           NOT NULL, -- Skrót hasła uzytkownika - SHA-256.
    first_name                                VARCHAR(50)        NOT NULL, -- Imię użytkownika
    last_name                                 VARCHAR(80)        NOT NULL, -- Nazwisko użytkownika
    phone_number                              VARCHAR(15),                 -- Numer telefonu użytkownika
    pesel                                     CHAR(11)                     -- Numer pesel użytkownika
        CONSTRAINT acc_pesel_unique UNIQUE,
    active                                    BOOL DEFAULT TRUE  NOT NULL, -- Pole pozwalające na blokowanie konta użytkownika, domyślnie wartość true (nie zablokowane).
    enabled                                   BOOL DEFAULT FALSE NOT NULL, -- Pole reprezentujące czy konto zostało aktywowane po rejestracji, domyśnie wartość fałsz (nie aktywowane).
    is_dark_mode                              bool default false,
    last_successful_login                     TIMESTAMPTZ,                 -- Pole reprezentujące datę ostatniego logowania użytkownika
    last_successful_login_ip                  VARCHAR(256),                -- Pole reprezentujące ip ostatniego logowania użytkownika
    last_unsuccessful_login                   TIMESTAMPTZ,                 -- Pole reprezentujące datę ostatniego nieudanego logowania użytkownika
    last_unsuccessful_login_ip                VARCHAR(256),                -- Pole reprezentujące ip ostatniego nieudanego logowania użytkownika
    unsuccessful_login_count_since_last_login INT  DEFAULT 0               -- Ilość nieudanych logowań od czasu ostatniego udanego logowania
        CONSTRAINT acc_unsuccessful_login_count_since_last_login_gr0 CHECK
            ( unsuccessful_login_count_since_last_login >= 0 ),            -- bigger than 0
    modified_by                               BIGINT,                      -- ID konta które ostatnio modyfikowało dane tabeli
    modification_date_time                    TIMESTAMPTZ,                 -- Data ostatniej modyfikacji tabeli
    modified_by_ip                            VARCHAR(256),
    last_block_unlock_modified_by             BIGINT,
    last_block_unlock_date_time               timestamptz,
    last_block_unlock_ip                      VARCHAR(256),

    created_by                                BIGINT             NOT NULL, -- ID konta które utworzyło tabelę,
    creation_date_time                        TIMESTAMPTZ        NOT NULL DEFAULT
        CURRENT_TIMESTAMP,                                                 -- Data utworzenia konta
    created_by_ip                             VARCHAR(256),
    email_recall                              BOOL DEFAULT FALSE NOT NULL,
    first_password_change                     BOOL DEFAULT FALSE NOT NULL,
    language                                  CHAR(2)            NOT NULL
        CONSTRAINT acc_language CHECK
            (language in ('pl', 'PL', 'en', 'EN')),                        -- Język konta
    version                                   BIGINT                       -- Wersja
        CONSTRAINT acc_version_gr0 CHECK (version >= 0)
);

-- Klucze obce dla tabeli accounts
ALTER TABLE accounts
    ADD FOREIGN KEY (created_by) REFERENCES accounts (id);
ALTER TABLE accounts
    ADD FOREIGN KEY (modified_by) REFERENCES accounts (id);
ALTER TABLE accounts
    ADD FOREIGN KEY (last_block_unlock_modified_by) REFERENCES accounts (id);


CREATE
    INDEX acc_id_index ON accounts (id);
CREATE
    INDEX acc_login_index ON accounts (login);
CREATE
    INDEX acc_modified_by_index ON accounts (modified_by);
CREATE
    INDEX acc_created_by_index ON accounts (created_by);

CREATE SEQUENCE accounts_seq -- Sekwencja wykorzystywana przy tworzeniu pola klucza głównego tabeli account
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE access_levels
(
    id                     BIGINT PRIMARY KEY,                                                -- klucz główny tabeli
    level                  VARCHAR(32) NOT NULL
        CONSTRAINT acc_lvl_level CHECK
            (level in
             ('level.patient', 'level.receptionist', 'level.doctor', 'level.administrator')), -- Poziom dostępu,
    account_id             BIGINT      NOT NULL,                                              -- Konto przypisane do poziomu dostepu
    active                 BOOL        NOT NULL DEFAULT TRUE,                                 -- Czy przypisany poziom dostępu jest aktywny, domyślnie prawda (jest aktywny). Pole pozwala na wyłączanie użytkownikom poziomów dostepu bez usuwania wiersza tabeli.
    CONSTRAINT acc_lvl_level_account_pair_unique UNIQUE (level, account_id),                  -- Para poziom dostepu i konta użytkownika jest unikalna
    version                BIGINT                                                             -- Wersja
        CONSTRAINT acc_lvl_version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,                    -- Data utworzenia tabeli
    created_by             BIGINT      NOT NULL,                                              -- ID konta które utworzyło tabelę
    created_by_ip          VARCHAR(256),
    modification_date_time TIMESTAMPTZ,                                                       -- Data ostatniej modyfikacji tabeli
    modified_by            BIGINT,                                                            -- Użytkownik który ostatni modyfikował tabelę
    modified_by_ip         VARCHAR(256)
);

-- Klucze obce dla tabeli access_levels
ALTER TABLE access_levels
    ADD FOREIGN KEY (created_by) REFERENCES accounts (id);
ALTER TABLE access_levels
    ADD FOREIGN KEY (modified_by) REFERENCES accounts (id);
ALTER TABLE access_levels
    ADD FOREIGN KEY (account_id) REFERENCES accounts (id);


CREATE
    INDEX acc_lvl_id_index ON access_levels (id);
CREATE
    INDEX acc_lvl_account_id_index ON access_levels (account_id);
CREATE
    INDEX acc_lvl_created_by_id_index ON access_levels (created_by);
CREATE
    INDEX acc_lvl_modified_by_id_index ON access_levels (modified_by);

CREATE VIEW glassfish_auth_view AS -- Widok wykorzystywany w procesie autentykacji i autoryzacji,
-- zawiera pola login, hasło i poziom dostepu dla każdej kombinacji tabel account i access_level dla której konto jest i aktywne i aktywowane oraz poziom dostępu jest aktywny
SELECT al.id, a.login, a.password, al.level
FROM accounts a,
     access_levels al
WHERE (al.account_id = a.id)
  AND (a.active = TRUE)
  AND (a.enabled = TRUE)
  AND (al.active = TRUE);


CREATE SEQUENCE access_levels_seq -- Sekwencja wykorzystywana przy tworzeniu pola klucza głównego tabeli access_level
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

-- struktura dla MOW

CREATE TABLE appointments
(
    id                     BIGINT PRIMARY KEY,                                   -- Klucz głowny tabeli
    doctor_id              BIGINT,                                               -- Id konta lekarza dla wizyty
    patient_id             BIGINT,                                               -- Id konta pacjenta dla wizyty
    appointment_date       TIMESTAMPTZ        NOT NULL,                          -- Data wizyty
    confirmed              BOOL DEFAULT FALSE NOT NULL,                          -- Czy wizyta została potwierdzona, domyślnie fałsz.
    canceled               BOOL DEFAULT FALSE NOT NULL,                          -- Czy wizyta została anulowana, domyślnie fałsz.
    rating                 NUMERIC(2, 1)                                         -- Ocena wizyty
        CONSTRAINT appoint_rating_between CHECK (rating >= 0 AND rating <= 5),
    version                BIGINT                                                -- Wersja
        CONSTRAINT appoint_version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ        NOT NULL DEFAULT CURRENT_TIMESTAMP,-- Data utworzenia wizyty
    created_by             BIGINT             NOT NULL,                          -- Konto które stworzyło wizytę
    created_by_ip          VARCHAR(256),
    modification_date_time TIMESTAMPTZ,                                          -- Data modyfikacji,
    modified_by            BIGINT,                                               -- Konto które modyfikowało ostatnio tabelę
    modified_by_ip         VARCHAR(256),
    confirmation_date_time timestamptz,
    confirmed_by           BIGINT,
    confirmed_by_ip        VARCHAR(256),
    cancellation_date_time timestamptz,
    canceled_by            BIGINT,
    canceled_by_ip         VARCHAR(256),
    reminder_mail_sent     BOOL DEFAULT FALSE NOT NULL,
    rate_mail_sent         BOOL DEFAULT FALSE NOT NULL
);

-- Klucze obce dla tabeli appointments
ALTER TABLE appointments
    ADD FOREIGN KEY (created_by) REFERENCES accounts (id);
ALTER TABLE appointments
    ADD FOREIGN KEY (modified_by) REFERENCES accounts (id);
ALTER TABLE appointments
    ADD FOREIGN KEY (doctor_id) REFERENCES accounts (id);
ALTER TABLE appointments
    ADD FOREIGN KEY (patient_id) REFERENCES accounts (id);
ALTER TABLE appointments
    ADD FOREIGN KEY (confirmed_by) REFERENCES accounts (id);
ALTER TABLE appointments
    ADD FOREIGN KEY (canceled_by) REFERENCES accounts (id);



CREATE
    INDEX appoint_id_index ON appointments (id);
CREATE
    INDEX appoint_doctor_id_index ON appointments (doctor_id);
CREATE
    INDEX appoint_patient_id_index ON appointments (patient_id);
CREATE
    INDEX appoint_created_by_id_index ON appointments (created_by);
CREATE
    INDEX appoint_modified_by_id_index ON appointments (modified_by);

CREATE SEQUENCE appointments_seq -- Sekwencja wykorzystywana do tworzenia kluczy głownych dla tabeli appointments
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

-- struktura dla MOD

-- Tabela reprezentująca dkoumentację medyczną użytkownika, będąca w relacji one to ona z tableką account, i one to many z wpisami w tabeli (tabela documentation_entries)
CREATE TABLE medical_documentations
(
    id                     BIGINT PRIMARY KEY,                             -- Klucz głowny tabeli
    patient_id             BIGINT      NOT NULL                            -- ID pacjenta którego dotyczy dokumentacja
        CONSTRAINT med_documentation_patient_id_unique UNIQUE,
    allergies              TEXT,                                           -- Tekstowy opis alergii pacjenta
    medications_taken      TEXT,                                           -- Tekstowy opis przyjmowanych lekarstw uzytkownika
    version                BIGINT                                          -- Wersja
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Data stworzenia wiersza w tabeli
    created_by             BIGINT      NOT NULL,                           -- Id użytkownika
    created_by_ip          VARCHAR(256),
    modification_date_time TIMESTAMPTZ,                                    -- Data ostatniej modyfikacji
    modified_by            BIGINT,                                         -- Konto które ostatnio modyfikowało dane tabeli
    modified_by_ip         VARCHAR(256)
);

-- Klucze obce dla tabeli medical_documentations
ALTER TABLE medical_documentations
    ADD FOREIGN KEY (created_by) REFERENCES accounts (id);
ALTER TABLE medical_documentations
    ADD FOREIGN KEY (modified_by) REFERENCES accounts (id);
ALTER TABLE medical_documentations
    ADD FOREIGN KEY (patient_id) REFERENCES accounts (id);


CREATE
    INDEX med_documentation_id_index ON medical_documentations (id);
CREATE
    INDEX med_documentation_patient_id_index ON medical_documentations (patient_id);
CREATE
    INDEX med_documentation_created_by_id_index ON medical_documentations (created_by);
CREATE
    INDEX med_documentation_modified_by_id_index ON medical_documentations (modified_by);


CREATE SEQUENCE medical_documentations_seq -- Sekwencja wykorzystywana do tworzenia klucza głownego dla tabeli medical_documentation
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

-- Tabela reprezentująca wpsiy w dokumentację medyczną użytkownika
CREATE TABLE documentation_entries
(
    id                     BIGINT PRIMARY KEY,                             -- Klucz głowny tabeli
    documentation_id       BIGINT      NOT NULL,                           -- Dokumentacja medyczna, do której odnosi się wpis
    doctor_id              BIGINT      NOT NULL,                           -- Lekarz, który tworzy wpis w dokumentacji
    was_done               bytea,                                          -- Tekst informujący co zostało wykonane na wizycie reprezentowanej przez wpis w dokumentacji
    to_be_done             bytea,                                          -- Tekst informujący co ma zostać wykonane na nastepnej wizycie
    version                BIGINT                                          -- Wersja
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Data utworzenia wiersza tabeli
    created_by             BIGINT      NOT NULL,                           -- Konto, które tworzyło wiersz tabeli
    created_by_ip          VARCHAR(256),
    modification_date_time TIMESTAMPTZ,                                    -- Data ostatniej modyfikacji wiersza w tabelik
    modified_by            BIGINT,                                         -- Konto które ostatnio modyfikowało wiersz w tabeli.
    modified_by_ip         VARCHAR(256)
);

-- Klucze obce dla tabeli documentation_entries
ALTER TABLE documentation_entries
    ADD FOREIGN KEY (created_by) REFERENCES accounts (id);
ALTER TABLE documentation_entries
    ADD FOREIGN KEY (modified_by) REFERENCES accounts (id);
ALTER TABLE documentation_entries
    ADD FOREIGN KEY (documentation_id) REFERENCES medical_documentations (id);
ALTER TABLE documentation_entries
    ADD FOREIGN KEY (doctor_id) REFERENCES accounts (id);

CREATE
    INDEX documentation_entry_id_index ON documentation_entries (id);
CREATE
    INDEX documentation_id_index ON documentation_entries (documentation_id);
CREATE
    INDEX documentation_created_by_index ON documentation_entries (created_by);
CREATE
    INDEX documentation_modified_by_index ON documentation_entries (modified_by);


CREATE SEQUENCE documentation_entries_seq -- Sekwencja uzywana do tworzenia pola klucza głównego w tabelii documentation_entries.
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

-- Tabela reprezentująca recepty
CREATE TABLE prescriptions
(
    id                     BIGINT PRIMARY KEY,                             -- Klucz główny tabeli
    expiration             TIMESTAMPTZ NOT NULL,                           -- data ważności recepty
    patient_id             BIGINT      NOT NULL,                           -- Id pacjenta, którego dotyczy recepta
    doctor_id              BIGINT      NOT NULL,                           -- Id lekarza który wystawił receptę
    medications            bytea,                                          -- Tekst informujący o przepisanych lekarstwach
    version                BIGINT                                          -- Wersja
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Czas utworzenia wiersza w tabeli
    created_by             BIGINT      NOT NULL,                           -- Konto które utworzyło wiersz w tabeli
    created_by_ip          VARCHAR(256),
    modification_date_time TIMESTAMPTZ,                                    -- Data ostatniej modyfikacji wiersza w tabeli
    modified_by            BIGINT,                                         -- Konto ostatnio modyfikujące wiersza w tabeli
    modified_by_ip         VARCHAR(256)
);

-- Klucze obce dla tabeli documentation_entries
ALTER TABLE prescriptions
    ADD FOREIGN KEY (created_by) REFERENCES accounts (id);
ALTER TABLE prescriptions
    ADD FOREIGN KEY (modified_by) REFERENCES accounts (id);
ALTER TABLE prescriptions
    ADD FOREIGN KEY (doctor_id) REFERENCES accounts (id);
ALTER TABLE prescriptions
    ADD FOREIGN KEY (patient_id) REFERENCES accounts (id);

CREATE
    INDEX prescription_id_index ON prescriptions (id);
CREATE
    INDEX prescription_doctor_id_index ON prescriptions (patient_id);
CREATE
    INDEX prescription_patient_index ON prescriptions (doctor_id);
CREATE
    INDEX prescription_created_by_id_index ON prescriptions (created_by);
CREATE
    INDEX prescription_modified_by_id_index ON prescriptions (modified_by);

CREATE SEQUENCE prescriptions_seq -- Sekwencja wykorzystywana do tworzenia klucza głownego w tabeli prescriptions
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE doctors_ratings
(
    id                     BIGINT PRIMARY KEY,                                  -- Klucz główny tabeli
    doctor_id              BIGINT           NOT NULL,                           -- Id lekarza, którego dotyczą statystyki ocen
    rates_sum              DOUBLE PRECISION NOT NULL DEFAULT 0.                 -- Suma wszystkich ocen wystawionych lekarzowi
        CONSTRAINT rates_sum_gr0 CHECK (rates_sum >= 0),
    rates_counter          INT              NOT NULL DEFAULT 0                  -- Ilość wszystkich wystawionych lekarzowi ocen
        CONSTRAINT rates_counter_gr0 CHECK (rates_counter >= 0),                -- Większe-równe 0
    active                 BOOL                      DEFAULT TRUE NOT NULL,
    version                BIGINT                                               -- Wersja
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Czas utworzenia wiersza w tabeli
    created_by             BIGINT           NOT NULL,                           -- Konto które utworzyło wiersz w tabeli
    created_by_ip          VARCHAR(256),
    modification_date_time TIMESTAMPTZ,                                         -- Data ostatniej modyfikacji wiersza w tabeli
    modified_by            BIGINT,                                              -- Konto ostatnio modyfikujące wiersza w tabeli
    modified_by_ip         VARCHAR(256)
);

ALTER TABLE doctors_ratings
    ADD FOREIGN KEY (doctor_id) REFERENCES accounts (id);
ALTER TABLE doctors_ratings
    ADD FOREIGN KEY (created_by) REFERENCES accounts (id);
ALTER TABLE doctors_ratings
    ADD FOREIGN KEY (modified_by) REFERENCES accounts (id);

CREATE
    INDEX doctor_rating_id_index ON doctors_ratings (id);
CREATE
    INDEX doctor_rating_doctor_index ON doctors_ratings (doctor_id);
CREATE
    INDEX doctor_rating_created_by_id_index ON doctors_ratings (created_by);
CREATE
    INDEX doctor_rating_modified_by_id_index ON doctors_ratings (modified_by);

CREATE SEQUENCE doctors_ratings_seq -- Sekwencja wykorzystywana do tworzenia klucza głownego w tabeli doctors_ratings
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

--------------------------------------------------
--                 UPRAWNIENIA                  --
--------------------------------------------------

ALTER TABLE accounts
    OWNER TO ssbd01admin;


ALTER TABLE access_levels
    OWNER TO ssbd01admin;

ALTER
    VIEW glassfish_auth_view
    OWNER TO ssbd01admin;

GRANT SELECT ON glassfish_auth_view TO ssbd01auth;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON accounts TO ssbd01mok;

GRANT SELECT ON accounts TO ssbd01mok, ssbd01mow, ssbd01mod;

GRANT SELECT, USAGE ON accounts_seq TO ssbd01mok;
GRANT SELECT ON accounts_seq TO ssbd01mod;
GRANT SELECT ON accounts_seq TO ssbd01mow;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON access_levels TO ssbd01mok;

GRANT SELECT ON access_levels TO ssbd01mok, ssbd01mow, ssbd01mod;

GRANT SELECT, USAGE ON access_levels_seq TO ssbd01mok;
GRANT SELECT ON access_levels_seq TO ssbd01mod;
GRANT SELECT ON access_levels_seq TO ssbd01mow;

-- UPRAWNIENIA dla MOW

ALTER TABLE appointments
    OWNER TO ssbd01admin;

ALTER TABLE doctors_ratings
    OWNER TO ssbd01admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON appointments TO ssbd01mow;

GRANT
    SELECT,
    USAGE
    ON appointments_seq TO ssbd01mow;

GRANT
    SELECT,
    INSERT,
    UPDATE
    ON doctors_ratings TO ssbd01mow;

GRANT
    SELECT,
    INSERT
    ON doctors_ratings TO ssbd01mok;

GRANT
    SELECT,
    USAGE
    ON doctors_ratings_seq TO ssbd01mow;

GRANT
    SELECT,
    USAGE
    ON doctors_ratings_seq TO ssbd01mok;

-- UPRAWNIENIA dla MOD

ALTER TABLE medical_documentations
    OWNER TO ssbd01admin;

ALTER TABLE documentation_entries
    OWNER TO ssbd01admin;

ALTER TABLE prescriptions
    OWNER TO ssbd01admin;

GRANT
    SELECT,
    INSERT,
    UPDATE
    ON medical_documentations TO ssbd01mod;

GRANT SELECT, USAGE ON medical_documentations_seq TO ssbd01mod;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON documentation_entries TO ssbd01mod;

GRANT SELECT, USAGE ON documentation_entries_seq TO ssbd01mod;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON prescriptions TO ssbd01mod;

GRANT SELECT, USAGE ON prescriptions_seq TO ssbd01mod;






