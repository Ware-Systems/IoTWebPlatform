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
import com.deviceiot.platform.util.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.mashape.unirest.http.ObjectMapper;

import lombok.*;

/**
 * Created by admin on 8/8/17.
 */

@Data
@Component
public class AWSConfig {

    @Autowired
    @Qualifier("httpClient")
    HttpClient httpClient;

    @Autowired
    EnvPropertyUtil env;

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
                    jacksonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    jacksonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
                new BasicAWSCredentials(env.getAccessKeyID(), env.getSecretAccessKey()))).
                withRegion(env.getAwsRegion()).build();

        return iotDataClient;
    }

    @Bean(name = "iotClient")
    public AWSIot getClient() {
        AWSIot iotClient = AWSIotClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(env.getAccessKeyID(), env.getSecretAccessKey()))).
                withRegion(env.getAwsRegion()).build();

        return iotClient;
    }



}
