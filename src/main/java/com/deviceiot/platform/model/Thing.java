package com.deviceiot.platform.model;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

/**
 * Created by admin on 8/10/17.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("device")
public class Thing {

    private String thingName;

    private Map<String, String> attributes;

}
