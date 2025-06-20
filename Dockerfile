FROM alpine/java:21-jre

# Install git
#RUN apt-get update && \
#    apt-get install -y git && \
#    apt-get clean \

# Copy your application JAR and set the entrypoint
COPY target/task2-0.0.1-SNAPSHOT.jar  app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
