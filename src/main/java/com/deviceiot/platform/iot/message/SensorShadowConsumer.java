/*
package com.deviceiot.platform.iot.message;

import javax.annotation.*;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.deviceiot.platform.iot.message.mqtt.*;

import lombok.extern.slf4j.*;

@Component
@Slf4j
public class SensorShadowConsumer implements MqttCallback {

    @Autowired
    MqttTemplate mqttTemplate;

    @PostConstruct
    public void connect() throws MqttException {
        mqttTemplate.subscribe("$aws/things/TemperatureSensor/shadow/update");
        mqttTemplate.setCallback(this);
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("Message arrived on topic {}. Contents: {}", topic, new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
*/
