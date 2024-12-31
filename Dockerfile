FROM openjdk:18-slim-buster
WORKDIR /app
COPY target/spring-boot_security-demo-0.0.1-SNAPSHOT.jar /app/BootSecurity.jar
COPY target/classes /app/classes
ENTRYPOINT ["java", "-jar", "BootSecurity.jar"]