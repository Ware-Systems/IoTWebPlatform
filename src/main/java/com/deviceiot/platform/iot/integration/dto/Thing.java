package com.deviceiot.platform.iot.integration.dto;

import java.util.*;

import lombok.*;

/**
 * Created by admin on 8/12/17.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Thing {

    private String thingName;

    private Map<String, String> attributes;
}
