package com.deviceiot.platform.model;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

/**
 * Created by admin on 8/12/17.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("sensor")
public class Sensor extends ThingShadow {

    private String sensorID;

    private String sensorName;

    private String sensorState;

    private Float tempreature;

}
