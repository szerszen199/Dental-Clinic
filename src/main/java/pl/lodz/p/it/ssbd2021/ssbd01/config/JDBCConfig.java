package pl.lodz.p.it.ssbd2021.ssbd01.config;

import java.sql.Connection;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// Ta pula połączeń jest na potrzeby tworzenia struktur przy wdrażaniu aplikacji
@DataSourceDefinition(

        name = "java:app/jdbc/ssbd01admin",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01admin",
        password = "9DQAn2+G+H=$K2Vu",
        serverName = "studdev.it.p.lodz.pl",
        portNumber = 5432,
        databaseName = "ssbd01",
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 1,
        maxIdleTime = 10) //Nie potrzebujemy przetrzymywać połączeń w tej puli

// Ta pula połączeń jest na potrzeby operacji realizowanych przez moduł aplikacji
@DataSourceDefinition(
        name = "java:app/jdbc/ssbd01mok",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01mok",
        password = "6d6bV9e@32vCCrUY",
        serverName = "studdev.it.p.lodz.pl",
        portNumber = 5432,
        databaseName = "ssbd01",
        transactional = true,
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED)

// Ta pula połączeń jest na potrzeby implementacji uwierzytelniania w aplikacji
@DataSourceDefinition(
        name = "java:app/jdbc/ssbd01auth",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01auth",
        password = "tF?reN@F7Yt9WM-=",
        serverName = "studdev.it.p.lodz.pl",
        portNumber = 5432,
        databaseName = "ssbd01")

@Stateless
public class JDBCConfig {

    //    Uczynienie z tej klasy komponentu Stateless
    //    i wstrzykniecie zarzadcy encji korzystajacego z ssbd00adminPU
    //    powoduje aktywowanie tej jednostki skladowania,
    //    a w konsekwencji utworzenie (z ew. usunieciem!) struktur w bazie danych
    //    @see persistence.xml
    @PersistenceContext(unitName = "ssbd01adminPU")
    private EntityManager em;
}