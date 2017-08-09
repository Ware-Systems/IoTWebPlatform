package com.deviceiot.platform.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.amazonaws.services.iot.client.*;

import io.swagger.annotations.*;
import lombok.extern.slf4j.*;

/**
 * Created by nitin ware on 8/8/17.
 */
@Api(tags = { "Device" }, description = "Device management")
@RestController
@RequestMapping("/device")
@Slf4j
public class DeviceController {

    @Autowired
    @Qualifier("awsIoTClient")
    AWSIotMqttClient awsIotClient;

    @ApiOperation(value = "Gets all devices", nickname = "getAllDevices")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelId", value = "Channel Id, 1:Desktop, 2:Mobile, 3:Tablet, 4:ProX, 5:ConsumerApp, 6:ProApp", allowableValues = "1,2,3,4,5,6", defaultValue = "1", required = true, dataType = "string", paramType = "header"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 401, message = "User Un-Authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getAllDevices(@PathVariable(value="deviceType") String deviceType) {
        log.debug("getAllDevices for {}", deviceType);
    }

}
