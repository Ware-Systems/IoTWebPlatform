package com.deviceiot.platform.data.dao.impl;

import java.util.*;

import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import com.deviceiot.platform.data.dao.*;
import com.deviceiot.platform.model.*;
import com.deviceiot.platform.util.*;

import lombok.extern.slf4j.*;

/**
 * Created by admin on 8/15/17.
 */
@Service
@Slf4j
public class SensorDAOImpl implements SensorDAO {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<Sensor> getSensorDataByPeriod(String sensorID, Integer periodValue, String period) {
        DateTime dateTime = null;
        switch (period) {
            case GlobalConstants.SECONDS:
                dateTime = DateTime.now().minusSeconds(periodValue);
                break;
            case GlobalConstants.MINUTES:
                dateTime = DateTime.now().minusMinutes(periodValue);
                break;
            case GlobalConstants.HOURS:
                dateTime = DateTime.now().minusHours(periodValue);
                break;
            case GlobalConstants.DAYS:
                dateTime = DateTime.now().minusDays(periodValue);
                break;
            case GlobalConstants.WEEKS:
                dateTime = DateTime.now().minusWeeks(periodValue);
                break;
            case GlobalConstants.MONTHS:
                dateTime = DateTime.now().minusMonths(periodValue);
                break;
        }
        Criteria criteria = Criteria.where("sensorID").is(sensorID).and("lastModifiedDate").gte(dateTime.toDate());
        Query query = new Query().addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"));
        return mongoTemplate.find(query, Sensor.class, "TempreatureSensor");
    }

}
