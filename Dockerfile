#FROM maven: 3.9-eclipse-temurin-17 AS build
#
#WORKDIR /app.
#
#COPY pom xml*
#RUN mvn dependency: go-offline
#
#COPY sc ./src
#RUN mvn Clean package -DskipTests
#
#FROM eclipse-temurin: 17-jdk
#
#WORKDIR /app
#
#COPY --from=build /app/target/*.jar app.jar
#
#EXPOSE 8080
#
#CMD ["java", "-jar", "app. jar"]

# Stage 1: Build
# We use the official Corretto 25 image and install Maven ourselves
# to ensure we have the exact JDK version you want.
FROM amazoncorretto:25-al2023 AS build
WORKDIR /app

# Install Maven on the Corretto image
RUN yum install -y maven

# Copy and build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM amazoncorretto:25-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]