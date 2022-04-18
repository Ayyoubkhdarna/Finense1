FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/uam-transporter.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]