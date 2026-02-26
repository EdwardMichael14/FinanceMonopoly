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
FROM maven:3.9.9-amazoncorretto-25-debian AS build
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the jar
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM amazoncorretto:25-alpine
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]