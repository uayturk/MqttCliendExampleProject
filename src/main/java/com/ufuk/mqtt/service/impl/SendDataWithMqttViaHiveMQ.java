package com.ufuk.mqtt.service.impl;


import com.hivemq.client.internal.mqtt.message.MqttMessage;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.ufuk.mqtt.service.SendDataWithMqttViaHiveMQService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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


  @Value("${connection.portHiveMq}")
  int portHiveMq;

  @Value("${connection.brokerUrlHiveMq}")
  String brokerUrlHiveMq;

  @Value("${connection.channelHiveMq}")
  String channelHiveMq;

  @Value("${connection.clientIdHiveMq}")
  String clientIdHiveMq;

  int qos = 0;

  private Mqtt3AsyncClient client;

  public static volatile boolean connectFlag = false;

  // Source :  https://www.hivemq.com/blog/mqtt-client-library-enyclopedia-hivemq-mqtt-client/

  @Override
  public void createClientAndConnectHiveMQ() throws IOException, JSONException {
    log.info("trying to start mqtt protocol via HiveMQ ");

    try {
      //Creating the Client
      client = MqttClient.builder()
          .useMqttVersion3()
          .identifier(UUID.randomUUID().toString()) //
          .serverHost(brokerUrlHiveMq)
          .serverPort(portHiveMq)
          //.sslConfig(sslConfig)
          .buildAsync();
      log.info("Successfully create client. " + client);

      //Creat connection
      log.info("trying to connect via HiveMQ ");
      client.connect()
          .whenComplete((connAck, throwable) -> {
            if (throwable != null) {
              log.info("Error during connect!");
              throwable.printStackTrace();
            } else {
              // Setup subscribes or start publishing if it is necessary here.
              log.info("Successfully connected!");
            }
          });


    } catch (Exception e) {
      log.info("Error occurred during create client and making connection." + e);
    }

  }

  //Connect
/*  @Override
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
  }*/

  @Override
  public void publishHiveMQ(String data) throws MqttException {

    String time = new Timestamp(System.currentTimeMillis()).toString();
    log.info("Publishing at: " +time+ " to topic \""+ channelHiveMq +"\" qos " + qos);

    client.publishWith()
        .topic(channelHiveMq)
        .payload(data.getBytes())
        .qos(MqttQos.fromCode(qos))
        .retain(true)
        .send()
        .whenComplete((mqtt3Publish, throwable) -> {
          if (throwable != null) {
            log.info("Error during publish!");
            throwable.printStackTrace();
          } else {
            log.info("Successfully published data" );
          }
        });

  }



}
