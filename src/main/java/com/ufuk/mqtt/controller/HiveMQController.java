package com.ufuk.mqtt.controller;

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.ufuk.mqtt.service.SendDataWithMqttViaHiveMQService;
import com.ufuk.mqtt.service.impl.MqttImpl;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HiveMQController {

  @Autowired
  private final MqttImpl mqttImpl;

  @Autowired
  private final SendDataWithMqttViaHiveMQService sendDataWithMqttViaHiveMQService;

  public HiveMQController(MqttImpl mqttImpl,
      SendDataWithMqttViaHiveMQService sendDataWithMqttViaHiveMQService) {
    this.mqttImpl = mqttImpl;
    this.sendDataWithMqttViaHiveMQService = sendDataWithMqttViaHiveMQService;
  }


  @RequestMapping(value = "/createClientHiveMQ", method = {RequestMethod.POST}, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Necessary doc is the below for createClientHiveMQ.\n",
      notes = "createClientHiveMQ method is creating client for HiveMQ mqtt .\n "
  )
  public void createClientHiveMQ() throws IOException, JSONException {
    sendDataWithMqttViaHiveMQService.createClientHiveMQ();
  }

  @RequestMapping(value = "/connect", method = {RequestMethod.POST}, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Necessary doc is the below for connect.\n",
      notes = "connect method is makes connection .\n "
  )
  public void connect(Mqtt3AsyncClient client) throws Exception {
    sendDataWithMqttViaHiveMQService.connect(client);
  }

  @RequestMapping(value = "/publishHiveMQ", method = {RequestMethod.POST}, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Necessary doc is the below for publishHiveMQ.\n",
      notes = "publishHiveMQ method is publish data for HiveMQ mqtt .\n "
  )
  public void publishHiveMQ() throws IOException, JSONException, MqttException {
    mqttImpl.publishHiveMQ();
  }


}
