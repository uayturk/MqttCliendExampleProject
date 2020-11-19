***What is MQTT (MQ Telemetry Transport)***

MQTT (MQ Telemetry Transport) is a messaging protocol that was created to address the need for a simple and lightweight method to transfer data to/from low-powered devices, such as those used in industrial applications.

***ASSESMENT***

<br/> MqttClientExampleProject is the RESTful service Api with embedded apache tomcat as servlet container. It use Java / Spring Boot as Back-End. In this simple project, you'll see how to use MQTT.

***RUN VIA USING ECLIPSE PAHO***

Firstly,for try to code you should create the channel account from ```https://thingspeak.com/``` . Don't forget the read this [link](https://uk.mathworks.com/help/thingspeak/publishtoachannelfeed.html) also. You can find more information.

When you done,you'll get your own Write/Read API keys ans Channel ID. Now you ready to go.  

If you wanna change default configuration,parameters set in src/main/resources/application.properties you need to give a new properties file with the following parameter;

```java -jar target/assessment-1.0.0-SNAPSHOT.jar --spring.config.location=file:/your_home/your_path/your_application.properties```

***RUN VIA USING HiveMQ MQTT CLIENT***

For running via HiveMQ you should follow links below:

1) https://www.hivemq.com/blog/mqtt-client-library-enyclopedia-hivemq-mqtt-client/  (What is HiveMQ)

2) http://www.mqtt-dashboard.com/ (Free HiveMQ Dashboard)

3) http://www.hivemq.com/demos/websocket-client/ (Follow your publish messages or you subscriptions)

Now you need to follow these steps under [3.link](http://www.hivemq.com/demos/websocket-client/)

3.1) In this project,we're publisher. Firstly you should connect from "Connection".

3.2) Then you need to subcribe from "Subscriptions". Use your topic name. In here,our topic is "ufukTestTopic".

3.3) Then run ```createClientAndConnectHiveMQ()``` method for create client and making a connection.

3.4) Finally run ```publishHiveMQ()``` method for publish your message or data.



***Swagger UI***

<br/>By default this assesment will be executed on 8089 port and you'll see the entire endpoints from http://localhost:8089/mqtt/swagger-ui.html#/mqtt-controller


***IDE***

<br/>For this service we used smart IDE intellij and you can easily start our spring boot application from ```src/main/java/com.ufuk.weatherApi/WeatherApiApplication``` class.

