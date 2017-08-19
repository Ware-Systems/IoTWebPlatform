package com.deviceiot.platform.service.impl;

import java.util.*;

import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.deviceiot.platform.data.dao.*;
import com.deviceiot.platform.iot.integration.*;
import com.deviceiot.platform.model.*;
import com.deviceiot.platform.service.*;
import com.deviceiot.platform.util.*;

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

    @Autowired
    SensorDAO sensorDAO;

    @Override
    public List<Sensor> getAllSensorsCurrentData(String thingName) {
        if(!thingsMap.isEmpty() && StringUtils.isEmpty(thingName)) {
            thingName = thingsMap.entrySet().iterator().next().getKey();
        }
        return deviceIoTFacade.getSensorShadow(thingName);
    }

    @Override
    public Sensor getSensorCurrentData(String thingName, String sensorID) {
        if(!thingsMap.isEmpty() && StringUtils.isEmpty(thingName)) {
            thingName = thingsMap.entrySet().iterator().next().getKey();
        }
        List<Sensor> sensors = deviceIoTFacade.getSensorShadow(thingName);
        Sensor sensorResponse =  sensors
                                    .stream()
                                    .filter(sensor -> sensor.getSensorID().equalsIgnoreCase(sensorID))
                                    .findAny()
                                    .orElse(null);
        return sensorResponse;
    }

    @Override
    public List<Sensor> getSensorCurrentByCriteria(String thingName, String sensorID,
                                    Integer seconds, Integer minutes, Integer hours,
                                    Integer days, Integer weeks, Integer months) {
        List<Sensor> sensorData = null;
        if(null != seconds)
            sensorData = sensorDAO.getSensorDataByPeriod(sensorID, seconds, GlobalConstants.SECONDS);
        else if(null != minutes)
            sensorData = sensorDAO.getSensorDataByPeriod(sensorID, minutes, GlobalConstants.MINUTES);
        else if(null != hours)
            sensorData = sensorDAO.getSensorDataByPeriod(sensorID, hours, GlobalConstants.HOURS);
        else if(null != days)
            sensorData = sensorDAO.getSensorDataByPeriod(sensorID, days, GlobalConstants.DAYS);
        else if(null != weeks)
            sensorData = sensorDAO.getSensorDataByPeriod(sensorID, weeks, GlobalConstants.WEEKS);
        else if(null != months)
            sensorData = sensorDAO.getSensorDataByPeriod(sensorID, months, GlobalConstants.MONTHS);
        return sensorData;
    }

    @Override
    public List<Sensor> updateSensorsData(String thingName, List<Sensor> sensors) {
        if(!thingsMap.isEmpty() && StringUtils.isEmpty(thingName)) {
            thingName = thingsMap.entrySet().iterator().next().getKey();
        }
        return deviceIoTFacade.updateSensorShadow(thingName, sensors);
    }
}
