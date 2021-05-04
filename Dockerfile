FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /opt/app

COPY ./build/libs/* ./app.jar

EXPOSE 8282

ENTRYPOINT ["java","-jar","app.jar"]