#
# BUILD STAGE
#
FROM maven:3.8.2-adoptopenjdk-11 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# DEPLOY STAGE
#
FROM jboss/wildfly:24.0.0.Final as deploy
# FIXME - copy instructuion (.war file ) can be improved in order not to be hardcoded
COPY --from=build /home/app/target/ssbd01-1.0.0.war /opt/jboss/wildfly/standalone/deployments/
