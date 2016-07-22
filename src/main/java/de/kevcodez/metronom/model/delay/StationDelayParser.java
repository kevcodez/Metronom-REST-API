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
