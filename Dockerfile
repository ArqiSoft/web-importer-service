FROM maven:3.5-jdk-8-alpine AS builder
COPY . /usr/src/web-importer-service
WORKDIR /usr/src/web-importer-service
RUN mvn -B -Pdocker package

FROM openjdk:8-jre
VOLUME /logs
RUN mkdir /temps
ENV SPRING_BOOT_APP web-importer-service.jar
ENV SPRING_BOOT_APP_JAVA_OPTS -Xmx256m -XX:NativeMemoryTracking=summary
WORKDIR /app
COPY --from=builder /usr/src/web-importer-service/target/$SPRING_BOOT_APP ./
ENTRYPOINT java $SPRING_BOOT_APP_JAVA_OPTS -jar $SPRING_BOOT_APP