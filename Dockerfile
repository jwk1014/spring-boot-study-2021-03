FROM openjdk:11.0.13

COPY build.gradle gradlew settings.gradle /application/
COPY gradle/wrapper/* /application/gradle/wrapper/
WORKDIR /application
RUN ./gradlew wrapper
COPY src src
COPY web/package.json web/package.json
COPY web/public web/public
COPY web/src web/src
RUN ./gradlew clean build -xtest
WORKDIR /application/build/libs
USER nobody
RUN echo $(ls -al /application/build/libs/|grep jar)
ENTRYPOINT ["sh", "-c", "java -jar demo-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081

# docker build -t demo .

# docker run --rm -p 808:8081 -e SPRING_PROFILES_ACTIVE=localdocker demo:latest