
FROM openjdk:17-jdk
MAINTAINER "vikas verma"
WORKDIR /app
COPY target/CrmApplication-0.0.1-SNAPSHOT.jar /app/crmapp.jar
EXPOSE 8080
CMD ["java", "-jar", "crmapp.jar"]