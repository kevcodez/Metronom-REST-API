package de.kevcodez.metronom.model.delay;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.kevcodez.metronom.model.alert.AlertParser;
import de.kevcodez.metronom.model.stop.Station;
import de.kevcodez.metronom.model.stop.StationProvider;
import de.kevcodez.metronom.utility.Exceptions;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;

/**
 * Parses the station delays from the Metronom station information endpoint.
 * 
 * @author Kevin Grüneberg
 *
 */
@Stateless
public class StationDelayParser {

  private static final String BASE_URL = "http://www.der-metronom.de/extern/etc.data.php?type=stationsauskunft";

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Inject
  private StationProvider stationProvider;

  @Inject
  private StationDelayConverter stationDelayConverter;

  public StationDelay findDelays(String stationName) {
    Station station = stationProvider.findStationByName(stationName);

    return findDelays(station);
  }

  public StationDelay findDelays(Station station) {
    if (station == null) {
      return null;
    }

    String uri = buildStationDelayUri(station);

    String result = AlertParser.getWebPabeSource(uri);

    try {
      JsonNode jsonNode = objectMapper.readTree(result);

      return stationDelayConverter.convert(station, jsonNode);
    } catch (IOException exc) {
      throw Exceptions.unchecked(exc);
    }
  }

  private String buildStationDelayUri(Station station) {
    UriBuilder uriBuilder = UriBuilder.fromUri(BASE_URL);
    uriBuilder.queryParam("bhf", station.getCode());

    return uriBuilder.build().toString();
  }

}
