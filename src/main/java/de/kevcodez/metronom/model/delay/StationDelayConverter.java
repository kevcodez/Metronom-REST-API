package de.kevcodez.metronom.model.delay;

import com.fasterxml.jackson.databind.JsonNode;

import de.kevcodez.metronom.model.stop.Station;
import de.kevcodez.metronom.model.stop.StationProvider;

import java.time.LocalTime;

import javax.inject.Inject;

/**
 * Converts the JSON response from Metronom to a {@link StationDelay}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class StationDelayConverter {

  @Inject
  private StationProvider stationProvider;

  public StationDelay convert(Station station, JsonNode node) {
    String timeAsString = node.get("stand").asText();

    JsonNode nodeDeparture = node.get("abfahrt");

    StationDelay stationDelay = new StationDelay(station, LocalTime.parse(timeAsString));

    for (JsonNode singleDeparture : nodeDeparture) {
      String time = singleDeparture.get("zeit").asText();
      String train = singleDeparture.get("zug").asText();
      String targetStationName = singleDeparture.get("ziel").asText();
      int delayInMinutes = singleDeparture.get("prognosemin").asInt();

      Station targetStation = stationProvider.findStationByName(targetStationName);
      DelayedDeparture delayedDeparture = new DelayedDeparture(train,
        targetStation, LocalTime.parse(time), delayInMinutes);

      stationDelay.getDepartures().add(delayedDeparture);
    }

    return stationDelay;
  }

}
