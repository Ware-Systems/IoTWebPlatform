package com.deviceiot.platform.model;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

/**
 * Created by admin on 8/11/17.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("lamp")
public class MyLamp extends ThingShadow {

    private String serialNumber;

    private String status;

}
