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
FROM docker.io/jboss/wildfly:25.0.0.Final as deploy
COPY --from=build /home/app/target/ssbd01-1.0.0.war /opt/jboss/wildfly/standalone/deployments/
COPY setup.cli /opt/jboss/wildfly/bin/setup.cli
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin123 --silent
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/bin/setup.cli
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
