FROM openjdk:11
VOLUME /tmp
EXPOSE 7000
ADD ./target/autenticador-0.0.1.jar autenticador.jar
ENTRYPOINT ["java","-jar","autenticador.jar"]