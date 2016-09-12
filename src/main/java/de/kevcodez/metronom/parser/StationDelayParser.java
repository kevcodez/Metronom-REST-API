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
package de.kevcodez.metronom.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.kevcodez.metronom.converter.StationDelayConverter;
import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.provider.StationProvider;
import de.kevcodez.metronom.utility.Exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

  private static final String BASE_URL = "http://www.der-metronom.de/livedata/etc?type=stationsauskunft";

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Inject
  private StationProvider stationProvider;

  @Inject
  private StationDelayConverter stationDelayConverter;

  /**
   * Finds the delay for the station with the given name.
   * 
   * @param stationName station name to search for
   * @return station delay
   */
  public StationDelay findDelays(String stationName) {
    Station station = stationProvider.findStationByName(stationName);

    return findDelays(station);
  }

  /**
   * Finds the delay for the given station.<br>
   * To avoid getting an access denied exception when retrieving the station delays, we have to get the cookies from any
   * other URL first. We retrieve a CSRF token and a CraftSessionID that are used for authentication.
   * 
   * @param station station to search for
   * @return station delay
   */
  public StationDelay findDelays(Station station) {
    if (station == null) {
      return null;
    }

    String uri = buildStationDelayUri(station);
    // Get cookies from any URL to avoid access denied exception
    Map<String, String> cookies = getCookiesFromUrl("http://www.der-metronom.de/ueber-metronom/wer-wir-sind/");
    String result = getStationDelays(cookies, uri);

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

  private String getStationDelays(Map<String, String> cookies, String uri) {
    try {
      URL myUrl = new URL(uri);
      URLConnection urlConn = myUrl.openConnection();

      String cookie = cookies.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
        .collect(Collectors.joining(";"));
      urlConn.setRequestProperty("Cookie", cookie);

      return getContentFromUrlConn(urlConn);
    } catch (IOException exc) {
      throw Exceptions.unchecked(exc);
    }
  }

  private String getContentFromUrlConn(URLConnection urlConn) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()))) {
      StringBuilder stringBuilder = new StringBuilder();
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        stringBuilder.append(inputLine);
      }

      return stringBuilder.toString();
    }
  }

  private Map<String, String> getCookiesFromUrl(String uri) {
    try {
      URL url = new URL(uri);
      URLConnection conn = url.openConnection();

      Map<String, List<String>> headerFields = conn.getHeaderFields();

      Set<String> headerFieldsSet = headerFields.keySet();
      Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

      while (hearerFieldsIter.hasNext()) {
        String headerFieldKey = hearerFieldsIter.next();

        if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
          List<String> headerFieldValue = headerFields.get(headerFieldKey);
          return headerFieldValue.stream()
            .map(header -> header.split(";\\s*"))
            .map(h -> h[0].split("="))
            .collect(Collectors.toMap(f -> f[0], f -> f[1]));
        }
      }
    } catch (IOException exc) {
      throw Exceptions.unchecked(exc);
    }

    return Collections.emptyMap();
  }

}
