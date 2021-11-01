FROM docker.io/library/postgres:14
ENV POSTGRES_DB ssbd01
ENV POSTGRES_USER ssbd01admin
ENV POSTGRES_PASSWORD ssbd01password
COPY sql/postgresql/init.sql /docker-entrypoint-initdb.d/1-init.sql
COPY sql/postgresql/createDB.sql /docker-entrypoint-initdb.d/2-create.sql
COPY sql/postgresql/data.sql /docker-entrypoint-initdb.d/3-data.sql
