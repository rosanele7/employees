FROM openjdk:22

ARG JAR_FILE=target/employees-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} employees.jar
CMD apt-get update -y

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/employees.jar"]