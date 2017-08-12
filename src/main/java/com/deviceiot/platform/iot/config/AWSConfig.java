package com.deviceiot.platform.iot.config;

import java.io.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.amazonaws.auth.*;
import com.amazonaws.services.iot.*;
import com.amazonaws.services.iotdata.*;
import com.fasterxml.jackson.core.*;
import com.mashape.unirest.http.ObjectMapper;

import lombok.*;

/**
 * Created by admin on 8/8/17.
 */

@Data
@Component
@PropertySources({
        @PropertySource(value = "classpath:aws-config.properties", ignoreResourceNotFound = true) })
public class AWSConfig {

    @Value("${clientEndpoint}")
    private String clientEndpoint;

    @Value("${clientId}")
    private String clientId;

    @Value("${certificateFile}")
    private String certificateFile;

    @Value("${privateKeyFile}")
    private String privateKeyFile;

    @Value("${accessKeyID}")
    private String accessKeyID;

    @Value("${secretAccessKey}")
    private String secretAccessKey;

    @Value("${region}")
    private String region;

    @Autowired
    @Qualifier("httpClient")
    HttpClient httpClient;

    @Bean(name = "httpClient")
    public HttpClient getHttpClient(){
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        return clientBuilder.build();
    }

    @Bean(name = "deviceObjectMapper")
    public ObjectMapper customDeviceObjectMapper() {
        return  new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Bean(name = "iotDataClient")
    public AWSIotData getDataClient() {
       AWSIotData iotDataClient = AWSIotDataClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(accessKeyID, secretAccessKey))).
                withRegion(region).build();

        return iotDataClient;
    }

    @Bean(name = "iotClient")
    public AWSIot getClient() {
        AWSIot iotClient = AWSIotClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(accessKeyID, secretAccessKey))).
                withRegion(region).build();

        return iotClient;
    }



}
