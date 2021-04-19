package pl.lodz.p.it.ssbd2021.ssbd01.config;

import java.sql.Connection;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


//@DataSourceDefinition(
//
//        name = "java:app/jdbc/ssbd01admin",
//        className = "org.postgresql.ds.PGSimpleDataSource",
//        user = "ssbd01admin",
//        password = "9DQAn2+G+H=$K2Vu",
//        serverName = "studdev.it.p.lodz.pl",
//        portNumber = 5432,
//        databaseName = "ssbd01",
//        initialPoolSize = 1,
//        minPoolSize = 0,
//        maxPoolSize = 1,
//        maxIdleTime = 10)


@DataSourceDefinition(
        name = "java:app/jdbc/ssbd01mok",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01mok",
        password = "6d6bV9e@32vCCrUY",
        serverName = "studdev.it.p.lodz.pl",
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
        password = "tF?reN@F7Yt9WM-=",
        serverName = "studdev.it.p.lodz.pl",
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
        password = "t2ymEPCbs^#8Qfeb",
        serverName = "studdev.it.p.lodz.pl",
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
        password = "?43DR#bQzEaceZb5",
        serverName = "studdev.it.p.lodz.pl",
        portNumber = 5432,
        databaseName = "ssbd01",
        transactional = true,
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 32,
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED)


@Stateless
public class JDBCConfig {


//    @PersistenceContext(unitName = "ssbd01adminPU")
//    private EntityManager em;
}