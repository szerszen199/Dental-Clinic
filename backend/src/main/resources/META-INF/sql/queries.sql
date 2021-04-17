-- uwierzytelnienie i autoryzacja
SELECT *
FROM glassfish_auth_view
WHERE (login = 'jnow123')
  AND (password = 'b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342');

-- poziomy dostępu
SELECT level
FROM glassfish_auth_view
WHERE login = 'jnow123';

