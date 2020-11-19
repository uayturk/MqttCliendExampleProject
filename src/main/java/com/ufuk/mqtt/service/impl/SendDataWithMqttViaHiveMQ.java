package com.ufuk.mqtt.service.impl;


import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.ufuk.mqtt.service.SendDataWithMqttViaHiveMQService;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service("mqttHiveMQ")
@Slf4j
public class SendDataWithMqttViaHiveMQ implements SendDataWithMqttViaHiveMQService {


  @Value("${connection.port}")
  int port;

  @Value("${connection.brokerUrlHiveMq}")
  String brokerUrlHiveMq;

  @Value("${connection.channel}")
  String channel;

  @Value("${connection.clientId}")
  String clientId;

  int qos = 0;

  private Mqtt3AsyncClient client;

  public static volatile boolean connectFlag = false;

  // Source :  https://www.hivemq.com/blog/mqtt-client-library-enyclopedia-hivemq-mqtt-client/

  //Creating the Client
  @Override
  public Mqtt3AsyncClient createClientHiveMQ() throws IOException, JSONException {
    log.info("trying to start mqtt protocol via HiveMQ ");

    try {
      client = MqttClient.builder()
          .useMqttVersion3()
          .identifier(UUID.randomUUID().toString()) //
          .serverHost(brokerUrlHiveMq)
          .serverPort(port)
          //.sslConfig(sslConfig)
          .buildAsync();
      log.info("Successfully create client. " + client);

      log.info("trying to connect and subscribe via HiveMQ ");
      client.connectWith().send().whenComplete((mqtt3ConnAck, throwable) -> {
        if (throwable != null) {
          connectFlag = false;
          log.info("Error during connect!");
          throwable.printStackTrace();
        } else {
          //Subscribe
          log.info("trying to make subscribe");
          connectFlag = true;
          client.subscribeWith()
              .topicFilter(UUID.randomUUID().toString())
              .callback(publish -> {
                log.info("received" + publish);
              })
              .send()
              .whenComplete((subAck, throwableq) -> {
                if (throwableq != null) {
                  throwableq.printStackTrace();
                } else {
                  log.info("Successfully subscribed");
                }
              });
        }
      });



      return client;
    } catch (Exception e) {
      log.info("Error occurred during returning client." );
      return (Mqtt3AsyncClient) e;
    }

  }

  //Connect
  @Override
  public void connect(Mqtt3AsyncClient client) throws Exception {
    log.info("trying to connect client.");
    try {
      client.connectWith().send().whenComplete((mqtt3ConnAck, throwable) -> {
        if (throwable != null) {
          connectFlag = false;
          log.info("Error during connect!");
          throwable.printStackTrace();
        } else {
          //Subscribe
          log.info("trying to make subscribe");
          connectFlag = true;
          client.subscribeWith()
              .topicFilter(UUID.randomUUID().toString())
              .callback(publish -> {
                log.info("received" + publish);
              })
              .send()
              .whenComplete((subAck, throwableq) -> {
                if (throwableq != null) {
                  throwableq.printStackTrace();
                } else {
                  log.info("Successfully subscribed");
                }
              });
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info("client successfully connected.");
  }

  @Override
  public void publishHiveMQ(String data) throws MqttException {

    String time = new Timestamp(System.currentTimeMillis()).toString();
    log.info("Publishing at: " +time+ " to topic \""+ channel +"\" qos " + qos);

    client.publishWith()
        .topic(channel)
        .payload(data.getBytes())
        .qos(MqttQos.fromCode(qos))
        .retain(true)
        .send()
        .whenComplete((mqtt3Publish, throwable) -> {
          if (throwable != null) {
            log.info("Error during publish!");
            throwable.printStackTrace();
          } else {
            // Handle successful publish, e.g. logging or incrementing a metric
          }
        });

  }



}
