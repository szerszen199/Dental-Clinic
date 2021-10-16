package pl.lodz.p.it.ssbd2021.ssbd01.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import java.sql.Connection;


//@DataSourceDefinition(
//        name = "java:app/jdbc/ssbd01admin",
//        className = "org.postgresql.ds.PGSimpleDataSource",
//        user = "ssbd01admin",
//        password = "TODO",
//        serverName = "TODO",
//        portNumber = 5432,
//        databaseName = "ssbd01",
//        initialPoolSize = 1,
//        minPoolSize = 0,
//        maxPoolSize = 1,
//        maxIdleTime = 10)


/**
 * Typ Jdbc config klasa konfiguracji JDBC.
 */
@DataSourceDefinition(
        name = "java:app/jdbc/ssbd01mok",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01mok",
        password = "password1",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "ssbd01",
        transactional = true,
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 32,
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED)


@DataSourceDefinition(
        name = "java:app/jdbc/ssbd01auth",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01auth",
        password = "password4",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "ssbd01",
        transactional = true,
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 32,
        isolationLevel = Connection.TRANSACTION_SERIALIZABLE)


@DataSourceDefinition(
        name = "java:app/jdbc/ssbd01mow",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01mow",
        password = "password2",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "ssbd01",
        transactional = true,
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 32,
        isolationLevel = Connection.TRANSACTION_SERIALIZABLE)


@DataSourceDefinition(
        name = "java:app/jdbc/ssbd01mod",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01mod",
        password = "password3",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "ssbd01",
        transactional = true,
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 32,
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED)


@Stateless
public class JDBCConfig {

}
