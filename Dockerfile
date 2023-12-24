FROM maven:3.8.1-jdk-8

WORKDIR /home/beilala/Documents/SAD/GroceryExpressProject-20-apr/GroceryExpressProject

COPY . .
RUN mvn clean install

CMD mvn spring-boot:run

