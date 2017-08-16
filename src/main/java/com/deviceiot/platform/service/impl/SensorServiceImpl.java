package com.deviceiot.platform.service.impl;

import java.util.*;

import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.deviceiot.platform.iot.integration.*;
import com.deviceiot.platform.model.*;
import com.deviceiot.platform.service.*;

import lombok.extern.slf4j.*;

/**
 * Created by admin on 8/15/17.
 */
@Service
@Slf4j
public class SensorServiceImpl implements ISensorService {

    @Autowired
    @Qualifier("thingsMap")
    HashMap<String, Thing> thingsMap;

    @Autowired
    DeviceIoTFacade deviceIoTFacade;

    @Override
    public List<Sensor> getAllSensorsData(String thingName) {
        if(!thingsMap.isEmpty() && StringUtils.isEmpty(thingName)) {
            thingName = thingsMap.entrySet().iterator().next().getKey();
        }
        return deviceIoTFacade.getSensorShadow(thingName);
    }

    @Override
    public List<Sensor> updateSensorsData(String thingName, List<Sensor> sensors) {
        if(!thingsMap.isEmpty() && StringUtils.isEmpty(thingName)) {
            thingName = thingsMap.entrySet().iterator().next().getKey();
        }
        return deviceIoTFacade.updateSensorShadow(thingName, sensors);
    }
}
