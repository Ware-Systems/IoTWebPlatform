package com.deviceiot.platform.iot.config;

import java.util.*;

import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.util.*;

import com.deviceiot.platform.iot.integration.*;
import com.deviceiot.platform.iot.integration.dto.*;
import com.deviceiot.platform.model.*;
import com.deviceiot.platform.model.Thing;

/**
 * Created by admin on 8/12/17.
 */
@Configuration
public class ThingConfig {

    @Autowired
    DeviceIoTFacade deviceIoTFacade;

    @Bean(name="thingsMap" )
    public HashMap<String, Thing> getAllThings () {
       HashMap<String, Thing> thingsMap = new HashMap<>();
       /*List<com.deviceiot.platform.iot.integration.dto.Thing> thingsList = deviceIoTFacade.getThings();
       if(!CollectionUtils.isEmpty(thingsList)) {
           thingsList.forEach(thingDTO -> {
               Thing thing = new Thing();
               BeanUtils.copyProperties(thingDTO, thing);
               thingsMap.put(thing.getThingName(), thing);
           });
       }*/
       return thingsMap;
    }

}
