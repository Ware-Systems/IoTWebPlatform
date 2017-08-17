package com.deviceiot.platform.controller;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.deviceiot.platform.iot.integration.*;
import com.deviceiot.platform.iot.integration.dto.*;
import com.deviceiot.platform.model.*;
import com.deviceiot.platform.model.MyLamp;
import com.deviceiot.platform.model.Sensor;
import com.deviceiot.platform.service.*;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.*;

import io.swagger.annotations.*;
import lombok.extern.slf4j.*;

/**
 * Created by admin on 8/12/17.
 */
@Api(tags = { "Sesnsor" }, description = "SensorShadow management")
@RestController
@RequestMapping("/sensor")
@Slf4j
public class SensorController {

    @Autowired
    DeviceIoTServiceHelper deviceIoTServiceHelper;

    @Autowired
    ISensorService sensorService;

    @ApiOperation(value = "Gets all sensors current state", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sensor> getAllSensors(@RequestParam(value = "thingName") String thingName) throws JSONException, MalformedURLException, UnirestException {
        return sensorService.getAllSensorsCurrentData(thingName);
    }

    @ApiOperation(value = "Gets sensor current state for given sensor id", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{sensorID}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Sensor getSensorData(@PathVariable(value = "sensorID") String sensorID, @RequestParam(value = "thingName") String thingName) {
        return sensorService.getSensorCurrentData(thingName, sensorID);
    }

    @ApiOperation(value = "Gets sensor for given sensor id for past requested timeframe ()", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{sensorID}/data", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sensor> getSensorDataSecondly(@PathVariable(value = "sensorID") String sensorID,
                                            @RequestParam(value = "seconds") Integer seconds,
                                            @RequestParam(value = "minutes") Integer minutes,
                                            @RequestParam(value = "hours") Integer hours,
                                            @RequestParam(value = "days") Integer days,
                                            @RequestParam(value = "weeks") Integer weeks,
                                            @RequestParam(value = "months") Integer months) {
        return null;
    }

    @ApiOperation(value = "Update Sensor Data", nickname = "updateSensor")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value="/{thingName}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sensor> udpateSensorShadow(@PathVariable(value="thingName") String thingName, @RequestBody List<Sensor> sensors){
       return sensorService.updateSensorsData(thingName, sensors);
    }


}
