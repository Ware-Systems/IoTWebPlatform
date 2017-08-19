package com.deviceiot.platform.data.dao;

import java.util.*;

import com.deviceiot.platform.model.*;

/**
 * Created by admin on 8/15/17.
 */
public interface SensorDAO {

    public List<Sensor> getSensorDataByPeriod(String sensorID, Integer periodValue, String period);

}
