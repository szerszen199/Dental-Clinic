#
# BUILD STAGE
#
FROM docker.io/library/maven:3.8.2-adoptopenjdk-11 as build
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app/
RUN mvn dependency:resolve
RUN mvn package

#
# DEPLOY STAGE
#
FROM docker.io/jboss/wildfly:24.0.0.Final as deploy
# FIXME - copy instructuion (.war file ) can be improved in order not to be hardcoded
COPY --from=build /home/app/target/ssbd01-1.0.0.war /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin123 --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
