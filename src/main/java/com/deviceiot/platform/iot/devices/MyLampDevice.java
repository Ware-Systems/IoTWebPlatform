package com.deviceiot.platform.iot.devices;

import java.util.*;

import com.amazonaws.services.iot.client.*;

import lombok.*;

/**
 * Created by admin on 8/8/17.
 */
@Data
public class MyLampDevice extends AWSIotDevice {

    @AWSIotDeviceProperty
    private String serialNumber;

    @AWSIotDeviceProperty
    private String status;

    public MyLampDevice(String thingName) {
        super(thingName);
    }

}
