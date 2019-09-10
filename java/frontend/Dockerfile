# Build stage
FROM maven:3.6-jdk-8 as build
WORKDIR /frontend
COPY ./ ./
RUN mvn clean package

# Create final image
FROM openjdk:8-jre-alpine
VOLUME /tmp
WORKDIR /frontend
COPY --from=build /frontend/target/frontend-spring-boot-1.0.0.jar ./
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/frontend/frontend-spring-boot-1.0.0.jar"]
