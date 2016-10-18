/**
 * MIT License
 * 
 * Copyright (c) 2016 Kevin Grüneberg
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package de.kevcodez.metronom.converter;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import de.kevcodez.metronom.model.delay.Departure;
import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.provider.StationProvider;

/**
 * Converts the JSON response from Metronom to a {@link StationDelay}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class StationDelayConverter {

  private static final Logger LOG = LoggerFactory.getLogger(StationDelayConverter.class);

  private static final Pattern PATTERN_TRACK = Pattern.compile("Gleis (\\d+)");

  @Inject
  private StationProvider stationProvider;

  /**
   * Converts the given JSON node and station to a station delay.
   * 
   * @param station station
   * @param node JSON node
   * @return converted station delay
   */
  public StationDelay convert(Station station, JsonNode node) {
    LOG.debug("converting json node {}", node);

    // the content is false if there is an error on the metronom endpoint
    if ("false".equals(node.toString())) {
      return null;
    }

    String timeAsString = node.get("stand").asText();
    LocalTime time = LocalTime.parse(timeAsString);

    StationDelay stationDelay = new StationDelay(station, time);

    JsonNode nodeDeparture = node.get("abfahrt");

    if (nodeDeparture == null) {
      return null;
    }

    if (nodeDeparture.getNodeType() == JsonNodeType.OBJECT) {
      // Parse single object
      Departure departure = parseDeparture(nodeDeparture);

      stationDelay.addDeparture(departure);
    } else {
      // Parse JSON array
      for (JsonNode singleDeparture : nodeDeparture) {
        Departure departure = parseDeparture(singleDeparture);

        stationDelay.addDeparture(departure);
      }
    }

    return stationDelay;
  }

  private Departure parseDeparture(JsonNode singleDeparture) {
    String time = singleDeparture.get("zeit").asText();
    String train = singleDeparture.get("zug").asText();
    String targetStationName = singleDeparture.get("ziel").asText();
    int delayInMinutes = singleDeparture.get("prognosemin").asInt();


    Station targetStation = stationProvider.findStationByName(targetStationName);
    Departure departure = new Departure(train, targetStation, LocalTime.parse(time), delayInMinutes);

    String prognose = singleDeparture.get("prognose").asText();

    Matcher matcher = PATTERN_TRACK.matcher(prognose);
    if (matcher.find()) {
      departure.setTrack(matcher.group(1));
    }

    if (prognose.toLowerCase().contains("fällt")) {
      departure.setCancelled(true);
    }

    return departure;
  }

}
