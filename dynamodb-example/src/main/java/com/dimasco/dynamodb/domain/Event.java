package com.dimasco.dynamodb.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@DynamoDBTable(tableName = "EVENT")
public class Event implements Serializable {
    public static final String CITY_INDEX = "City-Index";
    public static final String AWAY_TEAM_INDEX = "AwayTeam-Index";

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
