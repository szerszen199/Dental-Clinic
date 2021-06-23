
-- Dodanie 4 użytkowników, oraz nadanie im poziomów dostępów.
-- Użytkownik -1 ma reprezentować administratora oraz recepcjonistę będącego jednosczesnie pacjentem,
-- -2 lekarza, -3 pacjenta a -4 recepcjonistę.
-- Użykownik -4 posiada również odebrane uprawnienia do poziomu lekarza

INSERT INTO accounts (id, login ,email, password, first_name, last_name, phone_number, pesel, language, version, enabled, created_by, email_recall)
VALUES (-1,'jkow123', 'jkowalski@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Jan', 'Kowalski',
        '123456789', '73041234356', 'pl', 0, true, -1, false),
       (-2,'jnow123' ,'jnowak@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Jan', 'Nowak',
        '123456789', '80122856965', 'pl', 0, true, -2, false),
       (-3,'pzdr123', 'pzdrzalik@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Przemysław',
        'Zdrzalik', '123456789', '78051064296', 'pl', 0, true, -2, false),
       (-4,'mses123' ,'mseseseko@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Mobutu',
        'Sese Seko', '123456789', '73052927225', 'en', 0, true, -4, false);

INSERT INTO access_levels (id, level, account_id, active, created_by, version)
VALUES (-1, 'level.patient', -1, true, -1, 0),
       (-2, 'level.receptionist', -1, true, -1, 0),
       (-3, 'level.administrator', -1, true, -1, 0),
       (-4, 'level.doctor', -1, false, -1, 0),
       (-5, 'level.patient', -2, false, -1, 0),
       (-6, 'level.receptionist', -2, false, -1, 0),
       (-7, 'level.administrator', -2, false, -1, 0),
       (-8, 'level.doctor', -2, true, -1, 0),
       (-9, 'level.patient', -3, true, -1, 0),
       (-10, 'level.receptionist', -3, false, -1, 0),
       (-11, 'level.administrator', -3, false, -1, 0),
       (-12, 'level.doctor', -3, false, -1, 0),
       (-13, 'level.patient', -4, false, -1, 0),
       (-14, 'level.receptionist', -4, true, -1, 0),
       (-15, 'level.administrator', -4, false, -1, 0),
       (-16, 'level.doctor', -4, false, -1, 0);

INSERT INTO doctors_ratings (id, doctor_id, rates_sum, rates_counter, version, created_by, created_by_ip)
VALUES (-1, -2, 0.0, 0, 0, -2, '0:0:0:0:0:0:0:1');