DROP TABLE if exists PRESCRIPTIONS;
DROP TABLE if exists DOCUMENTATION_ENTRIES;
DROP TABLE if exists MEDICAL_DOCUMENTATIONS;
DROP TABLE IF EXISTS APPOINTMENTS;
DROP VIEW IF EXISTS GLASSFISH_AUTH_VIEW;
DROP TABLE IF EXISTS ACCESS_LEVELS;
DROP TABLE IF EXISTS ACCOUNTS;


CREATE TABLE ACCOUNTS
(
    ID                                        BIGINT PRIMARY KEY,
    email                                     VARCHAR(100)     NOT NULL
        CONSTRAINT acc_email_unique UNIQUE,                                                                                    -- size?
    password                                  CHAR(64)         NOT NULL,
    first_name                                VARCHAR(50)      NOT NULL,
    last_name                                 VARCHAR(80)      NOT NULL,
    phone_number                              VARCHAR(15),
    pesel                                     CHAR(11)
        CONSTRAINT acc_pesel_unique UNIQUE,
    active                                    INT DEFAULT 1    NOT NULL
        CONSTRAINT acc_active_in_1_0 CHECK (active IN (0, 1)),
    enabled                                   INT DEFAULT 0    NOT NULL
        CONSTRAINT acc_enabled_in_1_0 CHECK (enabled IN (0, 1)),
    last_successful_login                     BIGINT,
    last_successful_login_ip                  VARCHAR(15),
    last_unsuccessful_login                   BIGINT,
    last_unsuccessful_login_ip                VARCHAR(15),
    unsuccessful_login_count_since_last_login INT DEFAULT 0
        CONSTRAINT acc_unsuccessful_login_count_since_last_login_gr0 CHECK ( unsuccessful_login_count_since_last_login >= 0 ), -- bigger than 0
    modified_by                               BIGINT
        CONSTRAINT acc_modified_by_fk REFERENCES ACCOUNTS (ID) NULL,
    modification_date                         BIGINT,
    created_by                                BIGINT           NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    creation_date                             BIGINT,
    language                                  CHAR(2)
        CONSTRAINT acc_languages_available_values CHECK (language IN ('en', 'pl', 'EN', 'PL')),
    version                                   BIGINT
        CONSTRAINT acc_version_gr0 CHECK (version >= 0)
);
CREATE INDEX acc_ID_pk ON ACCOUNTS (ID);
CREATE INDEX acc_email_index ON ACCOUNTS (email);

CREATE TABLE ACCESS_LEVELS
(
    ID                     BIGINT PRIMARY KEY,
    level                  VARCHAR(16) NOT NULL,
    account_id             BIGINT      NOT NULL
        CONSTRAINT acc_lvl_account_fk REFERENCES ACCOUNTS (ID),
    active                 INT         NOT NULL DEFAULT 1
        CONSTRAINT acc_lvl_active_in_1_0 CHECK (active IN (0, 1)),
    CONSTRAINT acc_lvl_level_account_pair_unique UNIQUE (level, account_id),
    version                BIGINT
        CONSTRAINT acc_lvl_version_gr0 CHECK (version >= 0),
    creation_date_time     BIGINT      NOT NULL,
    created_by             BIGINT      NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time BIGINT,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES ACCOUNTS (ID)

);

CREATE VIEW GLASSFISH_AUTH_VIEW as
select a.email, a.password, al.level
from ACCOUNTS a,
     ACCESS_LEVELS al
where (al.account_id = a.id)
  and (a.active = 1)
  and (a.enabled = 1)
  and (al.active = 1);

CREATE INDEX acc_lvl_account_id ON ACCESS_LEVELS (account_id);


-- struktura dla MOW

CREATE TABLE APPOINTMENTS
(
    ID                     BIGINT PRIMARY KEY,
    doctor_ID              BIGINT
        CONSTRAINT appoint_doctor_ID_fk REFERENCES ACCOUNTS (ID)  NOT NULL,
    patient_ID             BIGINT
        CONSTRAINT appoint_patient_ID_fk REFERENCES ACCOUNTS (ID) NULL,
    appointment_date       BIGINT                                 NOT NULL,
    confirmed              INT DEFAULT 0                          NOT NULL
        CONSTRAINT appoint_confirmed_in_1_0 CHECK (confirmed IN (0, 1)),
    canceled               INT DEFAULT 0                          NOT NULL
        CONSTRAINT appoint_canceled_in_1_0 CHECK (canceled IN (0, 1)),
    rating                 NUMERIC(2, 1)
        CONSTRAINT appoint_rating_between CHECK (rating >= 0 AND rating <= 5),
    version                BIGINT
        CONSTRAINT appoint_version_gr0 CHECK (version >= 0),
    creation_date_time     BIGINT                                 NOT NULL,
    created_by             BIGINT                                 NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time BIGINT,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES ACCOUNTS (ID)

);

CREATE INDEX appoint_doctor_id_index ON APPOINTMENTS (doctor_ID);
CREATE INDEX appoint_patient_id_index ON APPOINTMENTS (patient_ID);

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
    creation_date_time     BIGINT NOT NULL,
    created_by             BIGINT NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time BIGINT,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES ACCOUNTS (ID)
);

CREATE INDEX documentation_id_pk ON MEDICAL_DOCUMENTATIONS (ID);
CREATE INDEX documentation_patient_id_fk ON APPOINTMENTS (patient_ID);

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
    creation_date_time     BIGINT NOT NULL,
    created_by             BIGINT NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time BIGINT,
    modified_by            BIGINT
        CONSTRAINT modified_by_ID_fk REFERENCES ACCOUNTS (ID)
);

CREATE INDEX documentation_entry_id_pk ON DOCUMENTATION_ENTRIES (ID);
CREATE INDEX documentation_id_fk ON MEDICAL_DOCUMENTATIONS (ID);

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
    creation_date_time     BIGINT NOT NULL,
    created_by             BIGINT NOT NULL
        CONSTRAINT created_by_id_fk REFERENCES ACCOUNTS (ID),
    modification_date_time BIGINT,
    modified_by            BIGINT
        CONSTRAINT modified_by_id_fk REFERENCES ACCOUNTS (ID)
);

CREATE INDEX prescription_id_pk ON PRESCRIPTIONS (ID);
CREATE INDEX patient_prescriptions_index ON PRESCRIPTIONS (patient_ID);

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
    SELECT
    ON ACCOUNTS TO ssbd01mow;

GRANT
    SELECT
    ON ACCOUNTS TO ssbd01mod;

GRANT
    SELECT
    ON ACCOUNTS TO ssbd01auth;

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
    UPDATE
    ON DOCUMENTATION_ENTRIES TO ssbd01mod;

GRANT
    SELECT,
    INSERT,
    UPDATE
    ON PRESCRIPTIONS TO ssbd01mod;
/*
INSERT INTO accounts (id, email, password, first_name, last_name, language, version, enabled)
VALUES (-1, 'jkowalski@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Jan', 'Kowalski',
        'pl', 0, 1);

INSERT INTO accounts (id, email, password, first_name, last_name, language, version, enabled)
VALUES (-2, 'jnowak@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Jan', 'Nowak', 'pl',
        0, 1);

INSERT INTO access_levels (id, level, account_id)
VALUES (-1, 'level.patient', -1);
INSERT INTO access_levels (id, level, account_id, active)
VALUES (-2, 'level.recep', -2, 0);
INSERT INTO access_levels (id, level, account_id)
VALUES (-3, 'level.doctor', -2);


-- uwierzytelnienie
SELECT *
FROM accounts
WHERE (email = 'jnowak@mail.com')
  AND (password = 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342')
  AND (active = 1)
  AND (enabled = 1);

-- autoryzacja
SELECT *
FROM access_levels
WHERE account_id = (SELECT id FROM accounts WHERE email = 'jnowak@mail.com')
  AND (active = 1);
-- poziomy dostępu
SELECT *
FROM access_levels
WHERE account_id = (SELECT id FROM accounts WHERE email = 'jnowak@mail.com');


*/