package com.deviceiot.platform.iot.integration;

import java.net.*;
import java.nio.*;
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
    public <T extends JsonNode> T  getThingShadowRest(String thingName, Class<T> responseType) throws MalformedURLException, UnirestException {
        log.info("getThingShadow");

        Unirest.setHttpClient(awsConfig.getHttpClient());

        String clientEndpoint = String.format("%s.%s.amazonaws.com", env.getAwsResourceID(), env.getAwsRegion(), env.getAwsResourcePort());

        GetRequest request = Unirest.get(String.format("https://%s/things/%s/shadow", clientEndpoint, thingName));
        log.info("Request created: " + request.toString());

        DeviceUnirestV4Signer signer = new DeviceUnirestV4Signer();
        request = signer.sign(request, env.getAccessKeyID(), env.getSecretAccessKey(), env.getAwsRegion(), "iotdata");

        HttpResponse<JsonNode> jsonResponse = request.asObject(responseType);
        log.info(String.format("Response: %d, %s", jsonResponse.getStatus(), jsonResponse.getStatusText()));
        log.info("Response: " + jsonResponse.getBody().toString());
        return (T)jsonResponse.getBody();
    }

    public ListThingsResult listThingsAsync() {
        ListThingsRequest request = new ListThingsRequest();
        ListThingsResult result = iotClient.listThings(request);
        return result;
    }

    public <T> T listThingShadowAsync(String thingName, Class<T> responseType) {
        T responseObj = null;
        GetThingShadowRequest sensorShadowRequest = new GetThingShadowRequest().withThingName(thingName);
        GetThingShadowResult sensorShadowResult = iotDataClient.getThingShadow(sensorShadowRequest);
        responseObj = objectMapper.readValue(sensorShadowResult.getPayload().toString(), responseType);
        return responseObj;
    }

    public <T> T updateThingShadowAsync(String thingName, Object requestObj, Class<T> responseType) {
        T responseObj = null;
        String sensorShadowsJson = objectMapper.writeValue(requestObj);

        ByteBuffer payload = ByteBuffer.wrap(sensorShadowsJson.getBytes());

        UpdateThingShadowRequest updateThingShadowRequest = new UpdateThingShadowRequest().withThingName(thingName);
        updateThingShadowRequest.setPayload(payload);
        UpdateThingShadowResult result = iotDataClient.updateThingShadow(updateThingShadowRequest);

        responseObj = objectMapper.readValue(result.getPayload().toString(), responseType);

        return responseObj;
    }

}
