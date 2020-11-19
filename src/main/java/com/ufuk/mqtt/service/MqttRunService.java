package com.ufuk.mqtt.service;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface MqttRunService {

  void publish() throws MqttException;

  void publishHiveMQ() throws MqttException;

}
