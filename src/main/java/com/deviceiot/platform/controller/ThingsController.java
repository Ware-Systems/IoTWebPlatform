package com.deviceiot.platform.controller;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.deviceiot.platform.iot.integration.*;
import com.deviceiot.platform.model.Thing;
import com.deviceiot.platform.service.*;
import com.mashape.unirest.http.exceptions.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.*;

/**
 * Created by nitin ware on 8/8/17.
 */
@Api(tags = { "Thing" }, description = "Thing management")
@RestController
@RequestMapping("/thing")
@Slf4j
public class ThingsController {

    @Autowired
    DeviceIoTServiceHelper deviceIoTServiceHelper;

    @Autowired
    IThingService thingService;

    @ApiOperation(value = "Gets all things", nickname = "getAllThings")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Thing> getAllThings() throws JSONException, MalformedURLException, UnirestException {
        return thingService.getAllThings();
    }

}
