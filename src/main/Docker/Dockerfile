FROM java:8

VOLUME /tmp

COPY tjlw-hbase.jar app.jar

RUN bash -c "touch /app.jar"

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=test", "--server.port=8089", "> /log/app.log"]

