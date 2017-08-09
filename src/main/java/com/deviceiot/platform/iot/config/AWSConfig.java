package com.deviceiot.platform.iot.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import com.amazonaws.services.iot.client.*;
import com.deviceiot.platform.iot.util.*;

import lombok.*;

/**
 * Created by admin on 8/8/17.
 */

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:aws-config.properties", ignoreResourceNotFound = true) })
@Data
public class AWSConfig {

    @Value("${clientEndpoint}")
    private String clientEndpoint;

    @Value("${clientId}")
    private String clientId;

    @Value("${certificateFile}")
    private String certificateFile;

    @Value("${privateKeyFile}")
    private String privateKeyFile;

    private AWSIotMqttClient awsIotClient;


    public void disconnectionAWSClient() throws AWSIotException {
        awsIotClient.disconnect();
    }

    @Bean(name="awsIoTClient" )
    public AWSIotMqttClient getAWSIoTClient() {
        String clientEndpoint = getClientEndpoint();
        String clientId = getClientId();

        String certificateFile = getCertificateFile();
        String privateKeyFile = getPrivateKeyFile();
        if (awsIotClient == null && certificateFile != null && privateKeyFile != null) {
            AWSUtil.KeyStorePasswordPair pair = AWSUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
        }

        if (awsIotClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
        }
        return awsIotClient;
    }

}
