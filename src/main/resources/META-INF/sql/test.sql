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
CREATE INDEX acc_ID_pk ON ACCOUNTS (ID);
CREATE INDEX acc_email_index ON ACCOUNTS (email);

CREATE SEQUENCE accounts_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;