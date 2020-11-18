package com.ufuk.mqtt.service.impl;

import com.ufuk.mqtt.service.SendDataWithMqttService;
import java.sql.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service("mqtt")
@Slf4j
public class SendDataWithMqtt implements SendDataWithMqttService {

  private MqttClient client;
  private MqttConnectOptions conOpt;

  @Value("${connection.port}")
  int port;

  @Value("${connection.brokerUrl}")
  String brokerUrl;

  @Value("${connection.channel}")
  String channel;

  @Value("${connection.clientId}")
  String clientId;

  int qos = 0;

  @Override
  public void startConnectionAndSendDataWithMqtt() {
    log.info("trying to start mqtt protocol");

    String tmpDir = System.getProperty("java.io.tmpdir");
    MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

    try {

      conOpt = new MqttConnectOptions();
      conOpt.setCleanSession(true);

      client = new MqttClient(brokerUrl, MqttClient.generateClientId(), dataStore);

      client.setCallback(new MqttCallback() {

        @Override
        public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
         log.info("Message Arrived!!");
          String time = new Timestamp(System.currentTimeMillis()).toString();
          log.info("Time:\t" +time +
              "  Topic:\t" + arg0 +
              "  Message:\t" + new String(arg1.getPayload()) +
              "  QoS:\t" + arg1.getQos());
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
          try {
            log.info("deliveryComplete method  : " + arg0.getMessageId()+" "+arg0.getMessage());
          } catch (MqttException e) {
            e.printStackTrace();
          }
        }

        @Override
        public void connectionLost(Throwable arg0) {
          log.info("Connection to " + brokerUrl + " lost!");
          log.info("Reconnecting..");
          try {
            client.connect(conOpt);
            log.info("Connected");
          } catch (MqttException e) {
            log.info("Connection error ! ");
            e.printStackTrace();
          }
        }

      });
      if(client.isConnected()){
        log.info("Already Connected");
        client.disconnect();
      }
      client.connect(conOpt);
      log.info("Successfully connected!");


    } catch (MqttException e) {
      e.printStackTrace();
      System.out.println("Unable to set up client: "+e.toString());
      System.exit(1);
    }
  }

  @Override
  public void publish(String data) throws  MqttException {
    String time = new Timestamp(System.currentTimeMillis()).toString();
    log.info("Publishing at: "+time+ " to topic \""+channel+"\" qos "+qos);

    // Create and configure a message
    MqttMessage message = new MqttMessage(data.getBytes());
    message.setQos(qos);
    message.setRetained(false);
    log.info("message : " + message);
    client.publish(channel, message);
  }

  @Override
  public void close() throws MqttException{
    client.disconnect();
    log.info("Disconnected");
  }


}
