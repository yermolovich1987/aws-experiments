package com.dimasco.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.dimasco.dynamodb.domain.Event;
import com.dimasco.dynamodb.function.EventFunction;
import com.dimasco.dynamodb.manager.DynamoDBManager;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Dummy test that demonstrates how to work with local DynamoDB instance.
 */
@Ignore
public class AppTest {
    private static final DynamoDBMapper MAPPER = DynamoDBManager.mapper();
    private static final String EVENT_TABLE = "EVENT";

    @BeforeClass
    public static void beforeClass() {
        AmazonDynamoDB amazonDynamoDB = DynamoDBManager.amazonDynamoDB();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

        try {
            CreateTableRequest createTableRequest = new CreateTableRequest();

            createTableRequest.setTableName(EVENT_TABLE);

            createTableRequest.setKeySchema( Arrays.asList(
                    new KeySchemaElement("homeTeam", KeyType.HASH),     //Partition key
                    new KeySchemaElement("eventDate", KeyType.RANGE)   //Sort key
            ));

            createTableRequest.setAttributeDefinitions(Arrays.asList(
                    //new AttributeDefinition("eventId", ScalarAttributeType.N),
                    new AttributeDefinition("eventDate", ScalarAttributeType.N),
                    //new AttributeDefinition("sport", ScalarAttributeType.S),
                    new AttributeDefinition("homeTeam", ScalarAttributeType.S),
                    new AttributeDefinition("awayTeam", ScalarAttributeType.S),
                    new AttributeDefinition("city", ScalarAttributeType.S)
                   // new AttributeDefinition("country", ScalarAttributeType.S)
            ));

            createTableRequest.setGlobalSecondaryIndexes(Arrays.asList(
                    new GlobalSecondaryIndex().withIndexName(Event.AWAY_TEAM_INDEX)
                            .withKeySchema(new KeySchemaElement("awayTeam", KeyType.HASH))
                            .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L))
                            .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)),
                    new GlobalSecondaryIndex().withIndexName(Event.CITY_INDEX)
                            .withKeySchema(new KeySchemaElement("city", KeyType.HASH))
                            .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L))
                            .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY))
            ));

            createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(10L, 10L));

            System.out.println("Attempting to create table; please wait...");
            TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
            TableUtils.waitUntilExists(amazonDynamoDB, EVENT_TABLE, 60000, 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddingNewEvent() {
        Event event = new Event();
        event.setEventId(1L);
        event.setEventDate(System.currentTimeMillis());
        event.setAwayTeam("away");
        event.setHomeTeam("home");
        event.setSport("Football");
        event.setCity("Kyiv");
        event.setCountry("Ukraine");

        MAPPER.save(event);
    }

    @Test
    public void testSearchFunction() {
        EventFunction eventFunction = new EventFunction();

        List<Event> events = eventFunction.getAllEventsHandler();

        // TODO Just to demonstrate that local DynamoDB is working
        System.out.println("Events: " + events.size());
    }
}
