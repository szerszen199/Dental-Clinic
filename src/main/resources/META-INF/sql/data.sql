
-- Dodanie 4 użytkowników, oraz nadanie im poziomów dostępów.
-- Użytkownik -1 ma reprezentować administratora oraz recepcjonistę będącego jednosczesnie pacjentem,
-- -2 lekarza, -3 pacjenta a -4 recepcjonistę.
-- Użykownik -4 posiada również odebrane uprawnienia do poziomu lekarza

INSERT INTO accounts (id, login ,email, password, first_name, last_name, language, version, enabled, created_by)
VALUES (-1,jkow123, 'jkowalski@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Jan', 'Kowalski',
        'pl', 0, true, -1),
       (-2,jnow123 ,'jnowak@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Jan', 'Nowak', 'pl',
        0, true, -2),
       (-3,pzdr123, 'pzdrzalik@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Przemysław',
        'Zdrzalik', 'pl',
        0, true, -2),
       (-4,mses123 ,'mseseseko@mail.com', 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342', 'Mobutu',
        'Sese Seko', 'en',
        0, true, -4);

INSERT INTO access_levels (id, level, account_id, created_by)
VALUES (-1, 'level.patient', -1, -1),
       (-2, 'level.receptionist', -1, -1),
       (-3, 'level.admin', -1, -1),
       (-4, 'level.doctor', -2, -1),
       (-5, 'level.patient', -3, -1),
       (-6, 'level.receptionist', -4, -1);
INSERT INTO access_levels (id, level, account_id, active, created_by)
VALUES (-7, 'level.doctor', -4, false, -1);



