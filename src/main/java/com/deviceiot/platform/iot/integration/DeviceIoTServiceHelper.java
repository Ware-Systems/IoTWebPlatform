package com.deviceiot.platform.iot.integration;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.amazonaws.services.iot.*;
import com.amazonaws.services.iot.model.*;
import com.amazonaws.services.iotdata.*;
import com.amazonaws.services.iotdata.model.*;
import com.deviceiot.platform.iot.config.*;
import com.deviceiot.platform.iot.integration.dto.*;
import com.deviceiot.platform.iot.integration.dto.MyLamp;
import com.deviceiot.platform.iot.integration.dto.Sensor;
import com.deviceiot.platform.model.*;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.exceptions.*;
import com.mashape.unirest.request.*;
import lombok.*;
import lombok.extern.slf4j.*;

/**
 * Created by admin on 8/10/17.
 */
@Service
@Slf4j
@Data
public class DeviceIoTServiceHelper {

    @Autowired
    private AWSConfig awsConfig;

    @Autowired
    @Qualifier("deviceObjectMapper")
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("iotDataClient")
    private AWSIotData iotDataClient;

    @Autowired
    @Qualifier("iotClient")
    private AWSIot iotClient;

    public JsonNode listThingsRestJson() throws UnirestException, MalformedURLException {
        Unirest.setHttpClient(awsConfig.getHttpClient());

        GetRequest request = Unirest.get("https://iot." + awsConfig.getRegion() + ".amazonaws.com/things");
        log.info("Request created: " + request.toString());

        DeviceUnirestV4Signer signer = new DeviceUnirestV4Signer();
        request = signer.sign(request, awsConfig.getAccessKeyID(), awsConfig.getSecretAccessKey(), awsConfig.getRegion(), "execute-api");

        HttpResponse<JsonNode> jsonResponse = request.asJson();
        log.info(String.format("Response: %d, %s", jsonResponse.getStatus(), jsonResponse.getStatusText()));
        return jsonResponse.getBody();
    }

    public ListThingsResult listThingsAsync() {
        ListThingsRequest request = new ListThingsRequest();
        ListThingsResult result = iotClient.listThings(request);
        return result;
    }

    public SensorShadow listSensorShadowAsync(String thingName) {
        GetThingShadowRequest sensorShadowRequest = new GetThingShadowRequest().withThingName(thingName);
        GetThingShadowResult sensorShadowResult = iotDataClient.getThingShadow(sensorShadowRequest);
        SensorShadow sensorShadow = objectMapper.readValue(sensorShadowResult.getPayload().toString(), SensorShadow.class);
        return sensorShadow;
    }

    public SensorShadow updateThingShadowAsync(String thingName, SensorShadow sensorShadow) {

        String sensorShadowsJson = objectMapper.writeValue(sensorShadow);

        ByteBuffer payload = ByteBuffer.wrap(sensorShadowsJson.getBytes());

        UpdateThingShadowRequest updateThingShadowRequest = new UpdateThingShadowRequest().withThingName(thingName);
        updateThingShadowRequest.setPayload(payload);
        UpdateThingShadowResult result = iotDataClient.updateThingShadow(updateThingShadowRequest);

        SensorShadow sensorShadowResp = objectMapper.readValue(result.getPayload().toString(), SensorShadow.class);

        return sensorShadowResp;
    }

}
