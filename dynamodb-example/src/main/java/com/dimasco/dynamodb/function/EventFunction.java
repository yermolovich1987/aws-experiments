package com.dimasco.dynamodb.function;

import com.dimasco.dynamodb.dao.DynamoDBEventDao;
import com.dimasco.dynamodb.domain.Event;
import com.dimasco.dynamodb.dto.City;
import com.dimasco.dynamodb.dto.Team;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class EventFunction {
    private static final Logger LOG = Logger.getLogger(EventFunction.class);
    private static final String UNDEFINED = "undefined";

    private static final DynamoDBEventDao eventDao = DynamoDBEventDao.instance();

    public List<Event> getAllEventsHandler() {

        LOG.info("GetAllEvents invoked to scan table for ALL events");
        List<Event> events = eventDao.findAllEvents();
        LOG.info("Found " + events.size() + " total events.");
        return events;
    }

    public List<Event> getEventsForTeam(Team team) throws UnsupportedEncodingException {

        if (null == team || team.getTeamName().isEmpty() || team.getTeamName().equals(UNDEFINED)) {
            LOG.error("GetEventsForTeam received null or empty team name");
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }

        String name = URLDecoder.decode(team.getTeamName(), "UTF-8");
        LOG.info("GetEventsForTeam invoked for team with name = " + name);
        List<Event> events = eventDao.findEventsByTeam(name);
        LOG.info("Found " + events.size() + " events for team = " + name);

        return events;
    }

    public List<Event> getEventsForCity(City city) throws UnsupportedEncodingException {
        if (null == city || city.getCityName().isEmpty() || city.getCityName().equals(UNDEFINED)) {
            LOG.error("GetEventsForCity received null or empty city name");
            throw new IllegalArgumentException("City name cannot be null or empty");
        }

        String name = URLDecoder.decode(city.getCityName(), "UTF-8");
        LOG.info("GetEventsForCity invoked for city with name = " + name);
        List<Event> events = eventDao.findEventsByCity(name);
        LOG.info("Found " + events.size() + " events for city = " + name);

        return events;
    }

    public void saveOrUpdateEvent(Event event) {

        if (null == event) {
            LOG.error("SaveEvent received null input");
            throw new IllegalArgumentException("Cannot save null object");
        }

        LOG.info("Saving or updating event for team = " + event.getHomeTeam() + " , date = " + event.getEventDate());
        eventDao.saveOrUpdateEvent(event);
        LOG.info("Successfully saved/updated event");
    }

    public void deleteEvent(Event event) {

        if (null == event) {
            LOG.error("DeleteEvent received null input");
            throw new IllegalArgumentException("Cannot delete null object");
        }

        LOG.info("Deleting event for team = " + event.getHomeTeam() + " , date = " + event.getEventDate());
        eventDao.deleteEvent(event.getHomeTeam(), event.getEventDate());
        LOG.info("Successfully deleted event");
    }


}
