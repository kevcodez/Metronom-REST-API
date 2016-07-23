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
package de.kevcodez.metronom.model.alert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.kevcodez.metronom.model.station.StartAndTargetStation;
import de.kevcodez.metronom.model.station.StationFinder;
import de.kevcodez.metronom.utility.Exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Class to parse alert notifications from the Metronom SOAP endpoint.
 * 
 * @author Kevin Grüneberg
 *
 */
public class AlertParser {

  private static final String METRONOM_ALERT_URL = "http://www.der-metronom.de/extern/sharepoint/sharepoint.soap.php";

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Inject
  private AlertConverter alertConverter;

  @Inject
  private StationFinder stationFinder;

  /**
   * Parses the alerts from the Metronom SOAP endpoint.
   * 
   * @return list of alerts
   */
  public List<Alert> parseAlerts() {
    String pageSource = getWebPabeSource(METRONOM_ALERT_URL);
    try {
      JsonNode mainJsonNode = objectMapper.readTree(pageSource);
      JsonNode alertJsonNode = mainJsonNode.get("ListItem");

      List<Alert> alerts = new ArrayList<>();
      alertJsonNode.forEach(jsonAlert -> {
        Alert alert = alertConverter.convert(jsonAlert);
        StartAndTargetStation startAndTarget = stationFinder.findStartAndTarget(alert.getMessage());

        if (startAndTarget != null) {
          alert.setStationStart(startAndTarget.getStart());
          alert.setStationEnd(startAndTarget.getTarget());
        }

        alerts.add(alert);
      });

      return alerts;
    } catch (IOException exc) {
      throw Exceptions.unchecked(exc);
    }
  }

  /**
   * Gets the source of the web page at the given URL.
   * 
   * @param websiteUrl website URL
   * @return page source
   */
  public static String getWebPabeSource(String websiteUrl) {
    try {
      URL url = new URL(websiteUrl);
      URLConnection urlConn = url.openConnection();
      BufferedReader inputStream = new BufferedReader(
        new InputStreamReader(urlConn.getInputStream(), StandardCharsets.UTF_8));

      String inputLine;
      StringBuilder stringBuilder = new StringBuilder();

      while ((inputLine = inputStream.readLine()) != null) {
        stringBuilder.append(inputLine);
      }

      inputStream.close();

      return stringBuilder.toString();
    } catch (IOException exc) {
      throw Exceptions.unchecked(exc);
    }
  }

}
