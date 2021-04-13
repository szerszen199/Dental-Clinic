DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS documentation_entries;
DROP TABLE IF EXISTS medical_documentations;
DROP TABLE IF EXISTS appointments;
DROP VIEW IF EXISTS glassfish_auth_view;
DROP TABLE IF EXISTS access_levels;
DROP TABLE IF EXISTS accounts;


DROP SEQUENCE IF EXISTS accounts_seq;
DROP SEQUENCE IF EXISTS access_levels_seq;
DROP SEQUENCE IF EXISTS appointments_seq;
DROP SEQUENCE IF EXISTS medical_documentations_seq;
DROP SEQUENCE IF EXISTS documentation_entries_seq;
DROP SEQUENCE IF EXISTS prescriptions_seq;


CREATE TABLE accounts
(
    id                                        BIGINT PRIMARY KEY,
    email                                     VARCHAR(100)       NOT NULL
        CONSTRAINT acc_email_unique UNIQUE,                                                                                    -- size?
    password                                  CHAR(64)           NOT NULL,
    first_name                                VARCHAR(50)        NOT NULL,
    last_name                                 VARCHAR(80)        NOT NULL,
    phone_number                              VARCHAR(15),
    pesel                                     CHAR(11)
        CONSTRAINT acc_pesel_unique UNIQUE,
    active                                    BOOL DEFAULT TRUE  NOT NULL,
    enabled                                   BOOL DEFAULT FALSE NOT NULL,
    last_successful_login                     TIMESTAMPTZ,
    last_successful_login_ip                  VARCHAR(15),
    last_unsuccessful_login                   TIMESTAMPTZ,
    last_unsuccessful_login_ip                VARCHAR(15),
    unsuccessful_login_count_since_last_login INT  DEFAULT 0
        CONSTRAINT acc_unsuccessful_login_count_since_last_login_gr0 CHECK ( unsuccessful_login_count_since_last_login >= 0 ), -- bigger than 0
    modified_by                               BIGINT
        CONSTRAINT acc_modified_by_fk REFERENCES accounts (id)   NULL,
    modification_date_time                    TIMESTAMPTZ,
    created_by                                BIGINT             NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES accounts (id),
    creation_date_time                        TIMESTAMPTZ        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    language                                  CHAR(2)
        CONSTRAINT acc_languages_available_values CHECK (language IN ('en', 'pl', 'EN', 'PL')
            ),
    version                                   BIGINT
        CONSTRAINT acc_version_gr0 CHECK (version >= 0)
);
CREATE
    INDEX acc_id_index ON accounts (id);
CREATE
    INDEX acc_email_index ON accounts (email);
CREATE
    INDEX acc_modified_by_index ON accounts (modified_by);
CREATE
    INDEX acc_created_by_index ON accounts (created_by);

CREATE SEQUENCE accounts_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE access_levels
(
    id                     BIGINT PRIMARY KEY,
    level                  VARCHAR(32) NOT NULL
        CONSTRAINT access_levels_level_values CHECK (level IN
                                                     ('level.patient', 'level.receptionist', 'level.admin', 'level.doctor') ),
    account_id             BIGINT      NOT NULL
        CONSTRAINT acc_lvl_account_fk REFERENCES accounts (id),
    active                 BOOL        NOT NULL DEFAULT TRUE,
    CONSTRAINT acc_lvl_level_account_pair_unique UNIQUE (level, account_id),
    version                BIGINT
        CONSTRAINT acc_lvl_version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by             BIGINT      NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES accounts (id),
    modification_date_time TIMESTAMPTZ,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES accounts (id)

);

CREATE
    INDEX acc_lvl_id_index ON access_levels (id);
CREATE
    INDEX acc_lvl_account_id_index ON access_levels (account_id);
CREATE
    INDEX acc_lvl_created_by_id_index ON access_levels (created_by);
CREATE
    INDEX acc_lvl_modified_by_id_index ON access_levels (modified_by);

CREATE VIEW glassfish_auth_view AS
SELECT a.email, a.password, al.level
FROM accounts a,
     access_levels al
WHERE (al.account_id = a.id)
  AND (a.active = TRUE)
  AND (a.enabled = TRUE)
  AND (al.active = TRUE);


CREATE SEQUENCE access_levels_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


-- struktura dla MOW

CREATE TABLE appointments
(
    id                     BIGINT PRIMARY KEY,
    doctor_id              BIGINT
        CONSTRAINT appoint_doctor_id_fk REFERENCES accounts (id)  NOT NULL,
    patient_id             BIGINT
        CONSTRAINT appoint_patient_id_fk REFERENCES accounts (id) NULL,
    appointment_date       TIMESTAMPTZ                            NOT NULL,
    confirmed              BOOL DEFAULT FALSE                     NOT NULL,
    canceled               BOOL DEFAULT FALSE                     NOT NULL,
    rating                 NUMERIC(2, 1)
        CONSTRAINT appoint_rating_between CHECK (rating >= 0 AND rating <= 5),
    version                BIGINT
        CONSTRAINT appoint_version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ                            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by             BIGINT                                 NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES accounts (id),
    modification_date_time TIMESTAMPTZ,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES accounts (id)

);

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

CREATE SEQUENCE appointments_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

-- struktura dla MOD

CREATE TABLE medical_documentations
(
    id                     BIGINT PRIMARY KEY,
    patient_id             BIGINT      NOT NULL
        CONSTRAINT patient_id_fk REFERENCES accounts (id),
    allergies              TEXT,
    medications_taken      TEXT,
    version                BIGINT
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by             BIGINT      NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES accounts (id),
    modification_date_time TIMESTAMPTZ,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES accounts (id)
);

CREATE
    INDEX med_documentation_id_index ON medical_documentations (id);
CREATE
    INDEX med_documentation_patient_id_index ON medical_documentations (patient_id);
CREATE
    INDEX med_documentation_created_by_id_index ON medical_documentations (created_by);
CREATE
    INDEX med_documentation_modified_by_id_index ON medical_documentations (modified_by);


CREATE SEQUENCE medical_documentations_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE documentation_entries
(
    id                     BIGINT PRIMARY KEY,
    documentation_id       BIGINT      NOT NULL
        CONSTRAINT documentation_id_fk REFERENCES medical_documentations (id),
    doctor_id              BIGINT      NOT NULL
        CONSTRAINT doctor_id_fk REFERENCES accounts (id),
    was_done               TEXT,
    to_be_done             TEXT,
    version                BIGINT
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by             BIGINT      NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES accounts (id),
    modification_date_time TIMESTAMPTZ,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES accounts (id)
);

CREATE
    INDEX documentation_entry_id_index ON documentation_entries (id);
CREATE
    INDEX documentation_id_index ON documentation_entries (documentation_id);
CREATE
    INDEX documentation_created_by_index ON documentation_entries (created_by);
CREATE
    INDEX documentation_modified_by_index ON documentation_entries (modified_by);


CREATE SEQUENCE documentation_entries_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE prescriptions
(
    id                     BIGINT PRIMARY KEY,
    patient_id             BIGINT      NOT NULL
        CONSTRAINT patient_id_fk REFERENCES accounts (id),
    doctor_id              BIGINT      NOT NULL
        CONSTRAINT doctor_id_fk REFERENCES accounts (id),
    medications            TEXT        NOT NULL,
    version                BIGINT
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by             BIGINT      NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES accounts (id),
    modification_date_time TIMESTAMPTZ,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES accounts (id)
);

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

CREATE SEQUENCE prescriptions_seq
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
    UPDATE
    ON accounts TO ssbd01mok;

GRANT SELECT ON accounts TO ssbd01mok, ssbd01mow;

GRANT SELECT, USAGE ON accounts_seq TO ssbd01mok;
GRANT SELECT ON accounts_seq TO ssbd01mod;
GRANT SELECT ON accounts_seq TO ssbd01mow;

GRANT
    SELECT,
    INSERT,
    UPDATE
    ON access_levels TO ssbd01mok;

GRANT SELECT ON access_levels TO ssbd01mok, ssbd01mow;

GRANT SELECT, USAGE ON access_levels_seq TO ssbd01mok;
GRANT SELECT ON access_levels_seq TO ssbd01mod;
GRANT SELECT ON access_levels_seq TO ssbd01mow;

-- UPRAWNIENIA dla MOW

ALTER TABLE appointments
    OWNER TO ssbd01admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON appointments TO ssbd01mow;

GRANT SELECT, USAGE ON appointments_seq TO ssbd01mow;

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






