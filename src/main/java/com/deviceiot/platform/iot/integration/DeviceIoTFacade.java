package com.deviceiot.platform.iot.integration;

import java.util.*;

import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.amazonaws.services.iot.model.*;
import com.deviceiot.platform.iot.integration.dto.*;

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


}
