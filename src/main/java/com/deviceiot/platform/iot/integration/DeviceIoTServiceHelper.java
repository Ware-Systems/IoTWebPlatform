package com.deviceiot.platform.iot.integration;

import java.io.*;
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

    public String updateThingShadowAsync(String thingName, MyLamp myLamp) throws IOException, UnirestException {

        String myLampJson = objectMapper.writeValue(myLamp);

        ByteBuffer payload = ByteBuffer.wrap(myLampJson.getBytes());

        UpdateThingShadowRequest updateThingShadowRequest = new UpdateThingShadowRequest().withThingName(thingName);
        updateThingShadowRequest.setPayload(payload);
        UpdateThingShadowResult result = iotDataClient.updateThingShadow(updateThingShadowRequest);

        byte[] bytes = new byte[result.getPayload().remaining()];
        result.getPayload().get(bytes);
        String resultString = new String(bytes);
        return resultString;
    }

}
