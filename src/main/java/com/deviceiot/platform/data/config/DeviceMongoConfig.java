package com.deviceiot.platform.data.config;

import java.net.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.*;
import org.springframework.data.mongodb.*;
import org.springframework.data.mongodb.core.*;

import com.mongodb.*;

/**
 * Created by admin on 8/15/17.
 */
@Configuration
public class DeviceMongoConfig {

    @Autowired
    Environment env;

    private MongoClientOptions.Builder getMongoClientOptionsBuilder() {
        MongoClientOptions.Builder builder =  new MongoClientOptions.Builder();
        builder.connectionsPerHost(50);
        builder.writeConcern(WriteConcern.JOURNALED);
        builder.readPreference(ReadPreference.secondaryPreferred());
       return builder;
    }

    /*@Bean
    public MongoClient mongoClient() throws UnknownHostException {
        MongoClientOptions.Builder builder =  getMongoClientOptionsBuilder();
        MongoClientOptions options = builder.build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(env.getProperty("mongo.server"), Integer.parseInt( env.getProperty("mongo.port"))), options);
        return mongoClient;
    }*/

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        String mongoURI = env.getProperty("mongo.uri");
        MongoClientURI mongoClientURI = new MongoClientURI(mongoURI, getMongoClientOptionsBuilder());
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClientURI);
        return mongoDbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
