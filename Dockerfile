FROM alpine/java:21-jre

# Install git
#RUN apt-get update && \
#    apt-get install -y git && \
#    apt-get clean \

# Copy your application JAR and set the entrypoint
COPY target/app-0.0.1.jar app.jar
COPY GIT_COMMIT /app/GIT_COMMIT

ENTRYPOINT ["java", "-jar", "/app.jar"]
