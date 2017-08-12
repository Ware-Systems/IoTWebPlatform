package com.deviceiot.platform.service;

import java.util.*;

import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import com.deviceiot.platform.iot.integration.*;
import com.deviceiot.platform.model.*;

import lombok.extern.slf4j.*;

/**
 * Created by admin on 8/12/17.
 */
@Service
@Slf4j
public class ThingServiceImpl implements IThingService {

    @Autowired
    DeviceIoTFacade deviceIoTFacade;

    @Override
    public List<Thing> getAllThings() {
        List<com.deviceiot.platform.iot.integration.dto.Thing> thingsList = deviceIoTFacade.getThings();
        if(!CollectionUtils.isEmpty(thingsList)) {
            List<Thing> things = new ArrayList<>();
            thingsList.forEach(thingDTO -> {
                Thing thing = new Thing();
                BeanUtils.copyProperties(thingDTO, thing);
                things.add(thing);
            });
            return things;
        } else
            return Collections.EMPTY_LIST;
    }
}
