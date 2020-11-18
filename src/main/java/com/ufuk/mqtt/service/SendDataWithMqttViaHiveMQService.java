package com.ufuk.mqtt.service;

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface SendDataWithMqttViaHiveMQService {

  Mqtt3AsyncClient createClientHiveMQ() throws IOException, JSONException;

  void connect(Mqtt3AsyncClient client) throws Exception;

  void publishHiveMQ(String data) throws MqttException;

}
