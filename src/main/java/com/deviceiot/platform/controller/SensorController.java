package com.deviceiot.platform.controller;

import java.net.*;
import java.util.*;
import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.deviceiot.platform.model.Sensor;
import com.deviceiot.platform.service.*;
import com.mashape.unirest.http.exceptions.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.*;

/**
 * Created by Nitin Ware on 8/12/17.
 */
@Api(tags = { "Sensor" }, description = "Sensor Management")
@RestController
@RequestMapping("/sensor")
@Slf4j
public class SensorController {

    @Autowired
    ISensorService sensorService;

    @ApiOperation(value = "Gets all sensors current state", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sensor> getAllSensorsData(@RequestParam(value = "thingName", required = false) String thingName) throws JSONException, MalformedURLException, UnirestException {
        return sensorService.getAllSensorsCurrentData(thingName);
    }

    @ApiOperation(value = "Gets sensor current state for given sensor id", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{sensorID}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Sensor getSensorData(@RequestParam(value = "thingName", required = false) String thingName, @PathVariable(value = "sensorID") String sensorID) {
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
                                            @RequestParam(value = "thingName", required = false) String thingName,
                                            @RequestParam(value = "seconds", required = false) Integer seconds,
                                            @RequestParam(value = "minutes", required = false) Integer minutes,
                                            @RequestParam(value = "hours", required = false) Integer hours,
                                            @RequestParam(value = "days", required = false) Integer days,
                                            @RequestParam(value = "weeks", required = false) Integer weeks,
                                            @RequestParam(value = "months", required = false) Integer months) {
        return sensorService.getSensorCurrentByCriteria(thingName, sensorID,seconds, minutes, hours, days, weeks, months);
    }

    @ApiOperation(value = "Gets sensor for given sensor id for past requested timeframe ()", nickname = "getAllSensors")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/data", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sensor> getSensorDataSecondly(@RequestParam(value = "thingName", required = false) String thingName,
            @RequestParam(value = "seconds", required = false) Integer seconds,
            @RequestParam(value = "minutes", required = false) Integer minutes,
            @RequestParam(value = "hours", required = false) Integer hours,
            @RequestParam(value = "days", required = false) Integer days,
            @RequestParam(value = "weeks", required = false) Integer weeks,
            @RequestParam(value = "months", required = false) Integer months) {
        return sensorService.getSensorCurrentByCriteria(thingName, null, seconds, minutes, hours, days, weeks,
                months);
    }

    @ApiOperation(value = "Update Sensor Data", nickname = "updateSensor")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sensor> udpateSensorShadow(@RequestParam(value = "thingName", required = false) String thingName, @RequestBody List<Sensor> sensors){
       return sensorService.updateSensorsData(thingName, sensors);
    }


}
