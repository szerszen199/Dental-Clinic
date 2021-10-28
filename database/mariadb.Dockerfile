FROM docker.io/library/mariadb:10.6.4
ENV MARIADB_DATABASE ssbd01
ENV MARIADB_USER ssbd01admin
ENV MARIADB_PASSWORD ssbd01
ENV MARIADB_ROOT_PASSWORD ssbd01
# TODO move password to env variable/secret
COPY sql/maria/init.sql /docker-entrypoint-initdb.d/1-init.sql
COPY sql/maria/createDB.sql /docker-entrypoint-initdb.d/2-create.sql
COPY sql/maria/data.sql /docker-entrypoint-initdb.d/3-data.sql
