package com.deviceiot.platform.iot.integration.dto;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

/**
 * Created by admin on 8/11/17.
 */
@Data
@JsonRootName("reported")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyLampReported {

    private String serialNumber;

    private String status;

}
