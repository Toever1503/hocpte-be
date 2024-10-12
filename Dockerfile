FROM openjdk:11.0.6-jdk
WORKDIR app
WORKDIR uploads
COPY build/libs/HocPTE-BE-1-0.0.1-SNAPSHOT.jar /app/app.jar
COPY test.txt /uploads/test.txt
ENV APP_PROFILE="PROD"
EXPOSE 8085
CMD ["java", "-jar", "/app/app.jar"]