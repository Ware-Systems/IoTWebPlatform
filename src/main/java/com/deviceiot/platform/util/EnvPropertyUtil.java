package com.deviceiot.platform.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;

import lombok.*;

@Data
@Component
public class EnvPropertyUtil {

    @Value("${"+GlobalConstants.MQTT_ROOT_CA+"}")
    private String mqttRootCA;

    @Value("${"+GlobalConstants.MQTT_CLIENT_CERT+"}")
    private String mqttClientCert;

    @Value("${"+GlobalConstants.MQTT_PRIVATE_KEY+"}")
    private String mqttPrivateKey;

    @Value("${"+GlobalConstants.AWS_RESOURCE_ID+"}")
    private String awsResourceID;

    @Value("${"+GlobalConstants.AWS_REGION+"}")
    private String awsRegion;

    @Value("${"+GlobalConstants.AWS_RESOURCE_PORT+"}")
    private String awsResourcePort;

    @Value("${"+GlobalConstants.ACCESS_KEY_ID+"}")
    private String accessKeyID;

    @Value("${"+GlobalConstants.SECRET_ACCESS_KEY_ID+"}")
    private String secretAccessKey;

}
