***What is MQTT (MQ Telemetry Transport)***

MQTT (MQ Telemetry Transport) is a messaging protocol that was created to address the need for a simple and lightweight method to transfer data to/from low-powered devices, such as those used in industrial applications.

***ASSESMENT***

<br/> MqttClientExampleProject is the RESTful service Api with embedded apache tomcat as servlet container. It use Java / Spring Boot as Back-End. In this simple project, you'll see how to use MQTT.

***RUN***

Firstly,for try to code you should create the channel account from ```https://thingspeak.com/``` . Don't forget the read this [link](https://uk.mathworks.com/help/thingspeak/publishtoachannelfeed.html) also. You can find more information.

When you done,you'll get your own Write/Read API keys ans Channel ID. Now you ready to go.  

If you wanna change default configuration,parameters set in src/main/resources/application.properties you need to give a new properties file with the following parameter;

```java -jar target/assessment-1.0.0-SNAPSHOT.jar --spring.config.location=file:/your_home/your_path/your_application.properties```

***Swagger UI***

<br/>By default this assesment will be executed on 8089 port and you'll see the entire endpoints from http://localhost:8089/mqtt/swagger-ui.html#/mqtt-controller


***IDE***

<br/>For this service we used smart IDE intellij and you can easily start our spring boot application from ```src/main/java/com.ufuk.weatherApi/WeatherApiApplication``` class.

