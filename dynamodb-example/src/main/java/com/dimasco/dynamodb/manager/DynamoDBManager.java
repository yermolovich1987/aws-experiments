package com.dimasco.dynamodb.manager;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBManager {
    private static volatile DynamoDBManager instance;

    private static DynamoDBMapper mapper;
    private static AmazonDynamoDBClient client;

    private DynamoDBManager() {
        AWSCredentials credentials = new BasicAWSCredentials("test", "test");
        client = new AmazonDynamoDBClient(credentials);
        //client.setRegion(Region.getRegion(Regions.US_EAST_1));
        client.setEndpoint("http://localhost:8000");
        client.setSignerRegionOverride("local");
        mapper = new DynamoDBMapper(client);
    }

    public static DynamoDBManager instance() {

        if (instance == null) {
            synchronized(DynamoDBManager.class) {
                if (instance == null)
                    instance = new DynamoDBManager();
            }
        }

        return instance;
    }

    public static DynamoDBMapper mapper() {

        DynamoDBManager manager = instance();
        return manager.mapper;
    }

    public static AmazonDynamoDB amazonDynamoDB() {

        DynamoDBManager manager = instance();
        return manager.client;
    }
}
