package com.deviceiot.platform.iot.integration;

import java.util.*;

import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.amazonaws.services.iot.model.*;
import com.deviceiot.platform.iot.integration.dto.*;
import com.deviceiot.platform.iot.integration.dto.Sensor;
import com.deviceiot.platform.iot.integration.dto.Thing;
import com.deviceiot.platform.model.*;

import lombok.extern.slf4j.*;

/**
 * Created by admin on 8/12/17.
 */
@Slf4j
@Service
public class DeviceIoTFacade {

    @Autowired
    DeviceIoTServiceHelper deviceIoTServiceHelper;

    public List<Thing> getThings() {
        List<Thing> things = new ArrayList<>();
        ListThingsResult listThingsResult =  deviceIoTServiceHelper.listThingsAsync();
        List<ThingAttribute>  thingsList = listThingsResult.getThings();
        thingsList.forEach(thing -> {
            Thing thingDTO = new Thing();
            BeanUtils.copyProperties(thing, thingDTO);
            things.add(thingDTO);
        });
        return things;
    }

    public List<com.deviceiot.platform.model.Sensor> getSensorShadow(String thingName) {
        List<com.deviceiot.platform.model.Sensor> sensorsData = new ArrayList<>();
        SensorShadow sensorShadow = deviceIoTServiceHelper.listThingShadowAsync(thingName, SensorShadow.class);
        List<Sensor> sensors = sensorShadow.getState().getReported().getSensors();
        sensors.forEach(sensor -> {
            com.deviceiot.platform.model.Sensor sensorResponse = new com.deviceiot.platform.model.Sensor();
            BeanUtils.copyProperties(sensor, sensorResponse);
            sensorsData.add(sensorResponse);
        });
        return sensorsData;
    }

    public List<com.deviceiot.platform.model.Sensor> updateSensorShadow(String thingName, List<com.deviceiot.platform.model.Sensor> sensors) {
        List<com.deviceiot.platform.model.Sensor> sensorsData = new ArrayList<>();

        List<Sensor> sensorsDTO = new ArrayList<>();
        sensors.forEach(sensorReq -> {
            Sensor sensorDTO = new Sensor();
            BeanUtils.copyProperties(sensorReq, sensorDTO);
            sensorsDTO.add(sensorDTO);
        });

        SensorReported sensorReported = new SensorReported();
        sensorReported.setSensors(sensorsDTO);
        SensorState sensorState = new SensorState();
        sensorState.setReported(sensorReported);
        SensorShadow sensorShadowReq = new SensorShadow();
        sensorShadowReq.setState(sensorState);

        SensorShadow sensorShadowResp = deviceIoTServiceHelper.updateThingShadowAsync(thingName, sensorShadowReq, SensorShadow.class);

        List<Sensor> sensorShadowReportResp = sensorShadowResp.getState().getReported().getSensors();
        sensorShadowReportResp.forEach(sensor -> {
            com.deviceiot.platform.model.Sensor sensorResponse = new com.deviceiot.platform.model.Sensor();
            BeanUtils.copyProperties(sensor, sensorResponse);
            sensorsData.add(sensorResponse);
        });
        return sensorsData;
    }

}
