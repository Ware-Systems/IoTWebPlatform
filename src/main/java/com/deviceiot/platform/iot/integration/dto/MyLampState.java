package com.deviceiot.platform.iot.integration.dto;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

/**
 * Created by admin on 8/11/17.
 */
@Data
@JsonRootName("state")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyLampState {

    private MyLampReported reported;

}
