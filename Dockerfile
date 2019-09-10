FROM java:8-jdk-alpine

COPY ./ffq-food-entry-service-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch ffq-food-entry-service-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar", "-Dmongo.fooditems.load=true", "ffq-food-entry-service-0.0.1-SNAPSHOT.jar"]  