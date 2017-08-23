package com.deviceiot.platform.iot.message.mqtt;

import javax.net.ssl.*;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import com.deviceiot.platform.iot.util.*;
import com.deviceiot.platform.util.*;
import lombok.extern.slf4j.*;

@Configuration
@Slf4j
public class MqttConfiguration {

    @Autowired
    EnvPropertyUtil env;

    @Bean
    public MqttTemplate mqttTemplate() throws Exception {

        String rootCA = env.getMqttRootCA();
        String clientCert = env.getMqttClientCert();
        String privateKey = env.getMqttPrivateKey();
        String resource = env.getAwsResourceID();
        String region = env.getAwsRegion();
        String port = env.getAwsResourcePort();
        SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(rootCA, clientCert, privateKey);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setSocketFactory(sslSocketFactory);
        options.setCleanSession(true);
        String urlToCall = String.format("%s://%s.%s.amazonaws.com:%s", GlobalConstants.SSL, resource, region, port);
        MqttTemplate mqttTemplate = new MqttTemplate(urlToCall);
        mqttTemplate.connect(options);
        log.info("Mqtt connected with mqtt broker at url : {}", urlToCall);
        return mqttTemplate;
    }

}
