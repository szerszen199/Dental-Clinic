INSERT INTO access_levels (id, level, account_id, created_by)
VALUES (-1, 'level.patient', -1, -1),
       (-2, 'level.recep', -1, -1),
       (-3, 'level.admin', -1, -1),
       (-4, 'level.doctor', -2, -1),
       (-5, 'level.patient', -3, -1),
       (-6, 'level.recep', -4, -1);
INSERT INTO access_levels (id, level, account_id, active, created_by)
VALUES (-7, 'level.recep', -2, 0, -1);


-- uwierzytelnienie
SELECT *
FROM accounts
WHERE (email = 'jnowak@mail.com')
  AND (password = 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342')
  AND (active = 1)
  AND (enabled = 1);