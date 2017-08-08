package com.deviceiot.platform;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

import springfox.documentation.swagger2.annotations.*;

/**
 * Created by admin on 8/8/17.
 */
@SpringBootApplication
@EnableSwagger2
public class DeviceIoTPlatform {

    public static void main(String[] args) {
        SpringApplication.run(DeviceIoTPlatform.class, args);
    }

}
