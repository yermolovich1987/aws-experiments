package com.dimasco.dynamodb.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by dimas on 10/29/16.
 */
@Getter
@Builder
@DynamoDBTable(tableName = "EVENT")
public class Event implements Serializable {
    private static final String CITY_INDEX = "City-Index";
    private static final String AWAY_TEAM_INDEX = "AwayTeam-Index";

    @DynamoDBAttribute
    private Long eventId;

    @DynamoDBRangeKey
    private Long eventDate;

    @DynamoDBAttribute
    private String sport;

    @DynamoDBHashKey
    private String homeTeam;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = AWAY_TEAM_INDEX)
    private String awayTeam;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = CITY_INDEX)
    private String city;

    @DynamoDBAttribute
    private String country;
}
