FROM maven:3.6.1-jdk-8-alpine AS MAVEN_IMAGE
ENV BUILD_DIR=/tmp/project
RUN mkdir -p $BUILD_DIR

WORKDIR $BUILD_DIR

COPY ./pom.xml $BUILD_DIR/
COPY ./library-model $BUILD_DIR/library-model
COPY ./library-app $BUILD_DIR/library-app
COPY ./library-batch $BUILD_DIR/library-batch
COPY ./validate-app $BUILD_DIR/validate-app

RUN mvn package -pl library-app -am



FROM openjdk:8-alpine

ENV SPRING_DATA_MONGODB_URI=mongodb://mongo:27017
ENV VALIDATE-APP_URL=http://validate-app:8090/api/data

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR

WORKDIR $PROJECT_DIR

COPY --from=MAVEN_IMAGE /tmp/project/library-app/target/library-app.jar $PROJECT_DIR/library-app.jar

EXPOSE 8080

CMD ["java", "-jar", "library-app.jar"]
