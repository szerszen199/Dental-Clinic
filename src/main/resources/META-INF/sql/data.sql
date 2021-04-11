
INSERT INTO accounts (id, email, password, first_name, last_name, language, version, enabled, created_by)
VALUES (-1, 'jkowalski@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Jan', 'Kowalski',
        'pl', 0, true, -1),
       (-2, 'jnowak@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Jan', 'Nowak', 'pl',
        0, true, -2),
       (-3, 'pzdrzalik@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Przemysław',
        'Zdrzalik', 'pl',
        0, true, -2),
       (-4, 'mseseseko@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Mobutu',
        'Sese Seko', 'en',
        0, true, -4);

INSERT INTO access_levels (id, level, account_id, created_by)
VALUES (-1, 'level.patient', -1, -1),
       (-2, 'level.recep', -1, -1),
       (-3, 'level.admin', -1, -1),
       (-4, 'level.doctor', -2, -1),
       (-5, 'level.patient', -3, -1),
       (-6, 'level.recep', -4, -1);
INSERT INTO access_levels (id, level, account_id, active, created_by)
VALUES (-7, 'level.doctor', -4, false, -1);



