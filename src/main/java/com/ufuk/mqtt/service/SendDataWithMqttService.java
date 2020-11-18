package com.ufuk.mqtt.service;

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface SendDataWithMqttService {

  void startConnectionAndSendDataWithMqtt() throws IOException, JSONException;

  void publish(String data) throws MqttPersistenceException, MqttException;

  void close() throws MqttException;

}
