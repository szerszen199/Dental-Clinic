DROP TABLE if exists PRESCRIPTIONS;
DROP TABLE if exists DOCUMENTATION_ENTRIES;
DROP TABLE if exists MEDICAL_DOCUMENTATIONS;
DROP TABLE IF EXISTS APPOINTMENTS;
DROP VIEW IF EXISTS GLASSFISH_AUTH_VIEW;
DROP TABLE IF EXISTS ACCESS_LEVELS;
DROP TABLE IF EXISTS ACCOUNTS;


DROP SEQUENCE IF EXISTS accounts_seq;
DROP SEQUENCE IF EXISTS access_levels_seq;
DROP SEQUENCE IF EXISTS appointments_seq;
DROP SEQUENCE IF EXISTS medical_documentations_seq;
DROP SEQUENCE IF EXISTS documentation_entries_seq;
DROP SEQUENCE IF EXISTS prescriptions_seq;


CREATE TABLE ACCOUNTS
(
    ID                                        BIGINT PRIMARY KEY,
    email                                     VARCHAR(100)       NOT NULL
        CONSTRAINT acc_email_unique UNIQUE,                                                                                    -- size?
    password                                  CHAR(64)           NOT NULL,
    first_name                                VARCHAR(50)        NOT NULL,
    last_name                                 VARCHAR(80)        NOT NULL,
    phone_number                              VARCHAR(15),
    pesel                                     CHAR(11)
        CONSTRAINT acc_pesel_unique UNIQUE,
    active                                    bool default true  NOT NULL,
    enabled                                   bool DEFAULT false NOT NULL,
    last_successful_login                     timestamptz,
    last_successful_login_ip                  VARCHAR(15),
    last_unsuccessful_login                   timestamptz,
    last_unsuccessful_login_ip                VARCHAR(15),
    unsuccessful_login_count_since_last_login INT  DEFAULT 0
        CONSTRAINT acc_unsuccessful_login_count_since_last_login_gr0 CHECK ( unsuccessful_login_count_since_last_login >= 0 ), -- bigger than 0
    modified_by                               BIGINT
        CONSTRAINT acc_modified_by_fk REFERENCES ACCOUNTS (ID)   NULL,
    modification_date                         timestamptz,
    created_by                                BIGINT             NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    creation_date                             timestamptz        not null default current_timestamp,
    language                                  CHAR(2)
        CONSTRAINT acc_languages_available_values CHECK (language IN ('en', 'pl', 'EN', 'PL')),
    version                                   BIGINT
        CONSTRAINT acc_version_gr0 CHECK (version >= 0)
);
CREATE INDEX acc_ID_index ON ACCOUNTS (ID);
CREATE INDEX acc_email_index ON ACCOUNTS (email);
CREATE INDEX acc_modified_by_index ON ACCOUNTS (modified_by);
CREATE INDEX acc_created_by_index ON ACCOUNTS (created_by);

CREATE SEQUENCE accounts_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE ACCESS_LEVELS
(
    ID                     BIGINT PRIMARY KEY,
    level                  VARCHAR(32) NOT NULL,
    account_id             BIGINT      NOT NULL
        CONSTRAINT acc_lvl_account_fk REFERENCES ACCOUNTS (ID),
    active                 bool        NOT NULL DEFAULT true,
    CONSTRAINT acc_lvl_level_account_pair_unique UNIQUE (level, account_id),
    version                BIGINT
        CONSTRAINT acc_lvl_version_gr0 CHECK (version >= 0),
    creation_date_time     timestamptz NOT NULL default current_timestamp,
    created_by             BIGINT      NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time timestamptz,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES ACCOUNTS (ID)

);
CREATE INDEX acc_lvl_ID_index ON ACCESS_LEVELS (id);
CREATE INDEX acc_lvl_account_id_index ON ACCESS_LEVELS (account_id);
CREATE INDEX acc_lvl_created_by_id_index ON ACCESS_LEVELS (created_by);
CREATE INDEX acc_lvl_modified_by_id_index ON ACCESS_LEVELS (modified_by);

CREATE VIEW GLASSFISH_AUTH_VIEW as
SELECT a.email, a.password, al.level
FROM ACCOUNTS a,
     ACCESS_LEVELS al
WHERE (al.account_id = a.id)
  AND (a.active = true)
  AND (a.enabled = true)
  AND (al.active = true);


CREATE SEQUENCE access_levels_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- struktura dla MOW

CREATE TABLE APPOINTMENTS
(
    ID                     BIGINT PRIMARY KEY,
    doctor_ID              BIGINT
        CONSTRAINT appoint_doctor_ID_fk REFERENCES ACCOUNTS (ID)  NOT NULL,
    patient_ID             BIGINT
        CONSTRAINT appoint_patient_ID_fk REFERENCES ACCOUNTS (ID) NULL,
    appointment_date       timestamptz                                 NOT NULL,
    confirmed              bool DEFAULT false                          NOT NULL,
    canceled               bool DEFAULT false                          NOT NULL,
    rating                 NUMERIC(2, 1)
        CONSTRAINT appoint_rating_between CHECK (rating >= 0 AND rating <= 5),
    version                BIGINT
        CONSTRAINT appoint_version_gr0 CHECK (version >= 0),
    creation_date_time     timestamptz                                 not null default current_timestamp,
    created_by             BIGINT                                 NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time timestamptz,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES ACCOUNTS (ID)

);

CREATE INDEX appoint_ID_index ON APPOINTMENTS (ID);
CREATE INDEX appoint_doctor_id_index ON APPOINTMENTS (doctor_ID);
CREATE INDEX appoint_patient_id_index ON APPOINTMENTS (patient_ID);
CREATE INDEX appoint_created_by_id_index ON APPOINTMENTS (created_by);
CREATE INDEX appoint_modified_by_id_index ON APPOINTMENTS (modified_by);

CREATE SEQUENCE appointments_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- struktura dla MOD

CREATE TABLE MEDICAL_DOCUMENTATIONS
(
    ID                     BIGINT PRIMARY KEY,
    patient_ID             BIGINT NOT NULL
        CONSTRAINT patient_id_fk REFERENCES ACCOUNTS (ID),
    allergies              TEXT,
    medications_taken      TEXT,
    version                BIGINT
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     timestamptz not null default current_timestamp,
    created_by             BIGINT NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time timestamptz,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES ACCOUNTS (ID)
);

CREATE INDEX med_documentation_id_index ON MEDICAL_DOCUMENTATIONS (ID);
CREATE INDEX med_documentation_patient_id_index ON MEDICAL_DOCUMENTATIONS (patient_ID);
CREATE INDEX med_documentation_created_by_id_index ON MEDICAL_DOCUMENTATIONS (created_by);
CREATE INDEX med_documentation_modified_by_id_index ON MEDICAL_DOCUMENTATIONS (modified_by);


CREATE SEQUENCE medical_documentations_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE DOCUMENTATION_ENTRIES
(
    ID                     BIGINT PRIMARY KEY,
    documentation_ID       BIGINT NOT NULL
        CONSTRAINT documentation_id_fk REFERENCES MEDICAL_DOCUMENTATIONS (ID),
    doctor_ID              BIGINT NOT NULL
        CONSTRAINT doctor_id_fk REFERENCES ACCOUNTS (ID),
    was_done               TEXT,
    to_be_done             TEXT,
    version                BIGINT
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     timestamptz not null default current_timestamp,
    created_by             BIGINT NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time timestamptz,
    modified_by            BIGINT
        CONSTRAINT modified_by_ID_fk REFERENCES ACCOUNTS (ID)
);

CREATE INDEX documentation_entry_id_index ON DOCUMENTATION_ENTRIES (ID);
CREATE INDEX documentation_id_index ON DOCUMENTATION_ENTRIES (documentation_ID);
CREATE INDEX documentation_created_by_index ON DOCUMENTATION_ENTRIES (created_by);
CREATE INDEX documentation_modified_by_index ON DOCUMENTATION_ENTRIES (modified_by);


CREATE SEQUENCE documentation_entries_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE PRESCRIPTIONS
(
    ID                     BIGINT PRIMARY KEY,
    patient_ID             BIGINT NOT NULL
        CONSTRAINT patient_id_fk REFERENCES ACCOUNTS (ID),
    doctor_ID              BIGINT NOT NULL
        CONSTRAINT doctor_id_fk REFERENCES ACCOUNTS (ID),
    medications            TEXT   NOT NULL,
    version                BIGINT
        CONSTRAINT version_gr0 CHECK (version >= 0),
    creation_date_time     timestamptz not null default current_timestamp,
    created_by             BIGINT NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time timestamptz,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES ACCOUNTS (ID)
);

CREATE INDEX prescription_id_index ON PRESCRIPTIONS (ID);
CREATE INDEX prescription_doctor_id_index ON PRESCRIPTIONS (patient_ID);
CREATE INDEX prescription_patient_index ON PRESCRIPTIONS (doctor_ID);
CREATE INDEX prescription_created_by_id_index ON PRESCRIPTIONS (created_by);
CREATE INDEX prescription_modified_by_id_index ON PRESCRIPTIONS (modified_by);

CREATE SEQUENCE prescriptions_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--------------------------------------------------
--                 UPRAWNIENIA                  --
--------------------------------------------------

ALTER TABLE ACCOUNTS
    OWNER TO ssbd01admin;

ALTER TABLE ACCESS_LEVELS
    OWNER TO ssbd01admin;

ALTER VIEW glassfish_auth_view
    OWNER TO ssbd01admin;

GRANT select on glassfish_auth_view to ssbd01auth;

GRANT
    SELECT,
    INSERT,
    UPDATE
    ON ACCOUNTS TO ssbd01mok;

GRANT
    SELECT,
    INSERT,
    UPDATE
    ON ACCESS_LEVELS TO ssbd01mok;

GRANT
    SELECT
    ON ACCESS_LEVELS TO ssbd01mow;

GRANT
    SELECT
    ON ACCESS_LEVELS TO ssbd01mod;

GRANT
    SELECT
    ON ACCESS_LEVELS TO ssbd01auth;

-- UPRAWNIENIA dla MOW


ALTER TABLE APPOINTMENTS
    OWNER TO ssbd01admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON APPOINTMENTS TO ssbd01mow;

-- UPRAWNIENIA dla MOD

ALTER TABLE MEDICAL_DOCUMENTATIONS
    OWNER TO ssbd01admin;

ALTER TABLE DOCUMENTATION_ENTRIES
    OWNER TO ssbd01admin;

ALTER TABLE PRESCRIPTIONS
    OWNER TO ssbd01admin;

GRANT
    SELECT,
    INSERT,
    UPDATE
    ON MEDICAL_DOCUMENTATIONS TO ssbd01mod;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON DOCUMENTATION_ENTRIES TO ssbd01mod;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE
    ON PRESCRIPTIONS TO ssbd01mod;






