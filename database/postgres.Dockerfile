FROM postgres:14
COPY sql/createDB.sql /docker-entrypoint-initdb.d/1.sql
COPY sql/data.sql /docker-entrypoint-initdb.d/2.sql
