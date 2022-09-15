FROM openjdk:17.0.1

WORKDIR /app/mscv-usuarios

COPY  ./pom.xml app/
COPY  ./mscv-usuarios .

RUN ./mvnw clean package -DskipTestt

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/mscv.usuarios-0.0.1-SNAPSHOT.jar"]
