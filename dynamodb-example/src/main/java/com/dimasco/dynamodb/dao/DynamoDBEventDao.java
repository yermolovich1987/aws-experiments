package com.dimasco.dynamodb.dao;

import com.dimasco.dynamodb.domain.Event;

import java.util.List;
import java.util.Optional;

/**
 * Created by dimas on 10/29/16.
 */
public class DynamoDBEventDao implements EventDao {

    @Override
    public List<Event> findAllEvents() {
        return null;
    }

    @Override
    public List<Event> findEventsByCity(String city) {
        return null;
    }

    @Override
    public List<Event> findEventsByTeam(String team) {
        return null;
    }

    @Override
    public Optional<Event> findEventByTeamAndDate(String team, Long eventDate) {
        return null;
    }

    @Override
    public void saveOrUpdateEvent(Event event) {

    }

    @Override
    public void deleteEvent(String team, Long eventDate) {

    }
}
