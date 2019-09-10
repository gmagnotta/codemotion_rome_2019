# Build stage
FROM maven:3.6-jdk-8 as build
WORKDIR /backend
COPY ./ ./
RUN mvn clean package

# Create final image
FROM openjdk:8-jre-alpine
VOLUME /tmp
WORKDIR /backend
COPY --from=build /backend/target/backend-spring-boot-1.0.1.jar ./
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/backend/backend-spring-boot-1.0.1.jar"]
