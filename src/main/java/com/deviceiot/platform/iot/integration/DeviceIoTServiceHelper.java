package com.deviceiot.platform.iot.integration;

import java.net.*;
import java.nio.*;
import java.time.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.amazonaws.services.iot.*;
import com.amazonaws.services.iot.model.*;
import com.amazonaws.services.iotdata.*;
import com.amazonaws.services.iotdata.model.*;
import com.deviceiot.platform.iot.config.*;
import com.deviceiot.platform.util.*;
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

    @Autowired
    private EnvPropertyUtil env;

    public JsonNode listThingsRestJson() throws UnirestException, MalformedURLException {
        Unirest.setHttpClient(awsConfig.getHttpClient());

        GetRequest request = Unirest.get("https://iot." + env.getAwsRegion() + ".amazonaws.com/things");
        log.info("Request created: " + request.toString());

        DeviceUnirestV4Signer signer = new DeviceUnirestV4Signer();
        request = signer.sign(request, env.getAccessKeyID(), env.getSecretAccessKey(), env.getAwsRegion(), "execute-api");

        HttpResponse<JsonNode> jsonResponse = request.asJson();
        log.info(String.format("Response: %d, %s", jsonResponse.getStatus(), jsonResponse.getStatusText()));
        return jsonResponse.getBody();
    }

    /**
     * Returns the state of a thing's shadow as known by AWS IoT cloud using REST call.
     *
     * @param thingName
     * @return
     */
    public <T> T  getThingShadowRest(String thingName, Class<T> responseType) {
        HttpResponse<T> response = null;
        log.info("getThingShadow");
        Unirest.setHttpClient(awsConfig.getHttpClient());
        Unirest.setObjectMapper(objectMapper);
        String clientEndpoint = String.format("%s.%s.amazonaws.com", env.getAwsResourceID(), env.getAwsRegion());

        GetRequest request = Unirest.get(String.format("https://%s/things/%s/shadow", clientEndpoint, thingName));
        log.info("Request created: " + request.toString());
        DeviceUnirestV4Signer signer = new DeviceUnirestV4Signer();
        try {
            request = signer.sign(request, env.getAccessKeyID(), env.getSecretAccessKey(), env.getAwsRegion(), "iotdata");
            response = request.asObject(responseType);
            log.info(String.format("Response: %d, %s", response.getStatus(), response.getStatusText()));
            log.info("Response: " + response.getBody().toString());
            Instant after = Instant.now();
        } catch (MalformedURLException | UnirestException ex) {
            ex.printStackTrace();
        }
        return null != response ? response.getBody() : null;
    }

    public ListThingsResult listThingsAsync() {
        ListThingsRequest request = new ListThingsRequest();
        ListThingsResult result = iotClient.listThings(request);
        return result;
    }

    public <T> T listThingShadowAsync(String thingName, Class<T> responseType) {
        GetThingShadowRequest sensorShadowRequest = new GetThingShadowRequest().withThingName(thingName);
        GetThingShadowResult sensorShadowResult = iotDataClient.getThingShadow(sensorShadowRequest);

        byte[] bytes = new byte[sensorShadowResult.getPayload().remaining()];
        sensorShadowResult.getPayload().get(bytes);
        String resultString = new String(bytes);
        T responseObj = objectMapper.readValue(resultString, responseType);
        return responseObj;
    }

    public <T> T updateThingShadowAsync(String thingName, Object requestObj, Class<T> responseType) {
        String sensorShadowsJson = objectMapper.writeValue(requestObj);

        ByteBuffer payload = ByteBuffer.wrap(sensorShadowsJson.getBytes());

        UpdateThingShadowRequest updateThingShadowRequest = new UpdateThingShadowRequest().withThingName(thingName);
        updateThingShadowRequest.setPayload(payload);
        UpdateThingShadowResult result = iotDataClient.updateThingShadow(updateThingShadowRequest);

        byte[] bytes = new byte[result.getPayload().remaining()];
        result.getPayload().get(bytes);
        String resultString = new String(bytes);

        T responseObj = objectMapper.readValue(resultString, responseType);

        return responseObj;
    }

}
