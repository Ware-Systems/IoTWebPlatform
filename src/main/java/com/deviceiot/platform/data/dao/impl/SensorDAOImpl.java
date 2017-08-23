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
import com.mongodb.*;

import lombok.extern.slf4j.*;

/**
 * Created by Nitin Ware on 8/15/17.
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
                dateTime = DateTime.now(DateTimeZone.UTC).minusSeconds(periodValue);
                break;
            case GlobalConstants.MINUTES:
                dateTime = DateTime.now(DateTimeZone.UTC).minusMinutes(periodValue);
                break;
            case GlobalConstants.HOURS:
                dateTime = DateTime.now(DateTimeZone.UTC).minusHours(periodValue);
                break;
            case GlobalConstants.DAYS:
                dateTime = DateTime.now(DateTimeZone.UTC).minusDays(periodValue);
                break;
            case GlobalConstants.WEEKS:
                dateTime = DateTime.now(DateTimeZone.UTC).minusWeeks(periodValue);
                break;
            case GlobalConstants.MONTHS:
                dateTime = DateTime.now(DateTimeZone.UTC).minusMonths(periodValue);
                break;
        }

        java.time.Instant instant = java.time.Instant.parse(dateTime.toString()); //Pass your date.
        Date timestamp = Date.from(instant);
        DBObject query = null;
        if(null != sensorID)
            query = QueryBuilder.start("sensorID").is(sensorID).and("lastModifiedDate").greaterThanEquals(timestamp).get();
        else
            query = QueryBuilder.start("lastModifiedDate").greaterThanEquals(timestamp).get();
        BasicQuery basicQuery = new BasicQuery(query);
        basicQuery.with(new Sort(Sort.Direction.DESC,"lastModifiedDate"));
        List<Sensor> sensors = mongoTemplate.find(basicQuery, Sensor.class, "TempreatureSensor");
        return sensors;
    }

}
