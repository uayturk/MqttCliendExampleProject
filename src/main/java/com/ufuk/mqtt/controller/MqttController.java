package com.ufuk.mqtt.controller;

import com.ufuk.mqtt.service.SendDataWithMqttService;
import com.ufuk.mqtt.service.impl.MqttImpl;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MqttController {

  @Autowired
  private final MqttImpl mqttImpl;

  @Autowired
  private final SendDataWithMqttService mqttService;

  public MqttController(MqttImpl mqttImpl, SendDataWithMqttService mqttService) {
    this.mqttImpl = mqttImpl;
    this.mqttService = mqttService;
  }

  @RequestMapping(value = "/publish", method = {RequestMethod.POST}, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Necessary doc is the below for publish.\n",
      notes = "publish method is testing mqtt protocol functionalities.\n "
  )
  public void publish() throws MqttException{
    mqttImpl.publish();
  }

  @RequestMapping(value = "/startConnectionAndSendDataWithMqtt", method = {RequestMethod.POST}, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Necessary doc is the below for startConnectionAndSendDataWithMqtt.\n",
      notes = "startConnectionAndSendDataWithMqtt start mqtt protocol and connect.\n "
  )
  public void startConnectionAndSendDataWithMqtt() throws Exception {
    mqttService.startConnectionAndSendDataWithMqtt();
  }

}
