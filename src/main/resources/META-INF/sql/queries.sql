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
