package com.deviceiot.platform.data.dao;

import java.util.*;

import com.deviceiot.platform.model.*;

/**
 * Created by admin on 8/15/17.
 */
public interface SensorDAO {

    public List<Sensor> getSensorDataBySeconds(String sensorName, Integer seconds);

    public List<Sensor> getSensorDataByMinutes(String sensorName, Integer minutes);

    public List<Sensor> getSensorDataByHours(String sensorName, Integer hours);

    public List<Sensor> getSensorDataByDays(String sensorName, Integer days);

    public List<Sensor> getSensorDataByWeeks(String sensorName, Integer weeks);

    public List<Sensor> getSensorDataByMonths(String sensorName, Integer months);

}
