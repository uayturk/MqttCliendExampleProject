package com.ufuk.mqtt.service.impl;

import com.ufuk.mqtt.service.MqttRunService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqttImpl implements MqttRunService {

  @Autowired
  public SendDataWithMqtt sendDataWithMqtt;

  @Override
  public void publish() throws MqttException {
    //sendDataWithMqtt.startConnectionAndSendDataWithMqtt();
    sendDataWithMqtt.publish("field1=23");
  }
}
