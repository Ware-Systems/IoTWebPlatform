package com.deviceiot.platform.controller;

import java.net.*;
import java.util.*;

import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.deviceiot.platform.iot.integration.*;
import com.deviceiot.platform.model.*;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.*;

import io.swagger.annotations.*;
import lombok.extern.slf4j.*;

/**
 * Created by admin on 8/12/17.
 */
@Api(tags = { "Sesnsor" }, description = "Sensor management")
@RestController
@RequestMapping("/sensor")
@Slf4j
public class SensorController {

    @Autowired
    DeviceIoTServiceHelper deviceIoTServiceHelper;

    @ApiOperation(value = "Gets all sensors current state", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sensor> getAllSensors() throws JSONException, MalformedURLException, UnirestException {
        /*JsonNode jsonResponse = deviceIoTServiceHelper.listThingsRestJson();
        JSONArray things = jsonResponse.getObject().getJSONArray("things");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < things.length(); i++) {
            JSONObject thing = things.getJSONObject(i);
            builder.append(thing.toString());
        }
        return builder.toString();*/
        return null;
    }

    @ApiOperation(value = "Gets sensor current state for given sensor id", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{sensorID}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Sensor getSensorData(@PathVariable(value = "sensorID") String sensorID) {
        return null;
    }

    @ApiOperation(value = "Gets sensor for given sensor id for past requested timeframe ()", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{sensorId}/data", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Sensor getSensorDataSecondly(@PathVariable(value = "sensorID") String sensorID,
                                        @RequestParam(value = "seconds") String seconds,
                                        @RequestParam(value = "seconds") String minutes,
                                        @RequestParam(value = "seconds") String hours,
                                        @RequestParam(value = "seconds") String weeks,
                                        @RequestParam(value = "seconds") String months) {
        return null;
    }


}
