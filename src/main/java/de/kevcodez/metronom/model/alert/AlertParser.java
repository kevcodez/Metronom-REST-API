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
import de.kevcodez.metronom.utility.WebsiteSourceDownloader;

import java.io.IOException;
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

  public static final String METRONOM_ALERT_URL = "http://www.der-metronom.de/extern/sharepoint/sharepoint.soap.php";

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Inject
  private AlertConverter alertConverter;

  @Inject
  private StationFinder stationFinder;
  
  @Inject
  private WebsiteSourceDownloader websiteSourceDownloader;

  /**
   * Parses the alerts from the Metronom SOAP endpoint.
   * 
   * @return list of alerts
   */
  public List<Alert> parseAlerts() {
    String pageSource = websiteSourceDownloader.getSource(METRONOM_ALERT_URL);
    return parseAlertsFromSource(pageSource);
  }

  private List<Alert> parseAlertsFromSource(String pageSource) {
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

}
