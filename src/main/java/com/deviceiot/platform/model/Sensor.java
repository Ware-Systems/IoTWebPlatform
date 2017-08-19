package com.deviceiot.platform.model;

import java.util.*;

import org.springframework.data.mongodb.core.mapping.*;

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
@Document(collection = "TempreatureSensor")
public class Sensor extends ThingShadow {

    private String sensorID;

    private String sensorName;

    private Integer sensorState;

    private Float tempreature;

    private Date lastModifiedDate;

}
