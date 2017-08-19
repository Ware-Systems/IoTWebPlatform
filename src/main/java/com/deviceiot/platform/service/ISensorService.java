package com.deviceiot.platform.service;

import java.util.*;

import com.deviceiot.platform.model.*;

/**
 * Created by admin on 8/15/17.
 */
public interface ISensorService {

    public List<Sensor> getAllSensorsCurrentData(String thingName);

    public Sensor getSensorCurrentData(String thingName, String sensorID);

    public List<Sensor> updateSensorsData(String thingName, List<Sensor> sensors);

    public List<Sensor> getSensorCurrentByCriteria(String thingName, String sensorID, Integer seconds, Integer minutes, Integer hours, Integer days, Integer weeks, Integer months);

}
