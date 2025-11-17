FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 8080
# COPY  entrypoint.sh /entrypoint.sh
# RUN chmod +x /entrypoint.sh
# ENTRYPOINT ["/entrypoint.sh"]
