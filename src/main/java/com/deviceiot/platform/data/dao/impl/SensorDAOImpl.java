package com.deviceiot.platform.data.dao.impl;

import java.util.*;

import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;

import com.deviceiot.platform.data.dao.*;
import com.deviceiot.platform.model.*;

/**
 * Created by admin on 8/15/17.
 */
public class SensorDAOImpl implements SensorDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Sensor> getSensorDataBySeconds(String sensorName, Integer seconds) {
        DateTime dateTime = DateTime.now().minusSeconds(seconds);
        Criteria criteria = Criteria.where("lastModifiedDate").gte(dateTime.toDate());
        Query query = new Query().addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"));
        return mongoTemplate.find(query, Sensor.class);
    }

    @Override
    public List<Sensor> getSensorDataByMinutes(String sensorName, Integer minutes) {
        DateTime dateTime = DateTime.now().minusMinutes(minutes);
        Criteria criteria = Criteria.where("lastModifiedDate").gte(dateTime.toDate());
        Query query = new Query().addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"));
        return mongoTemplate.find(query, Sensor.class);
    }

    @Override
    public List<Sensor> getSensorDataByHours(String sensorName, Integer hours) {
        DateTime dateTime = DateTime.now().minusHours(hours);
        Criteria criteria = Criteria.where("lastModifiedDate").gte(dateTime.toDate());
        Query query = new Query().addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"));
        return mongoTemplate.find(query, Sensor.class);
    }

    @Override
    public List<Sensor> getSensorDataByDays(String sensorName, Integer days) {
        DateTime dateTime = DateTime.now().minusDays(days);
        Criteria criteria = Criteria.where("lastModifiedDate").gte(dateTime.toDate());
        Query query = new Query().addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"));
        return mongoTemplate.find(query, Sensor.class);
    }

    @Override
    public List<Sensor> getSensorDataByWeeks(String sensorName, Integer weeks) {
        DateTime dateTime = DateTime.now().minusWeeks(weeks);
        Criteria criteria = Criteria.where("lastModifiedDate").gte(dateTime.toDate());
        Query query = new Query().addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"));
        return mongoTemplate.find(query, Sensor.class);
    }

    @Override
    public List<Sensor> getSensorDataByMonths(String sensorName, Integer months) {
        DateTime dateTime = DateTime.now().minusMonths(months);
        Criteria criteria = Criteria.where("lastModifiedDate").gte(dateTime.toDate());
        Query query = new Query().addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"));
        return mongoTemplate.find(query, Sensor.class);
    }

}
