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
package de.kevcodez.metronom.rest.impl;

import static java.util.stream.Collectors.toList;

import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.model.alert.AlertCache;
import de.kevcodez.metronom.model.route.Route;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.rest.AlertResource;
import de.kevcodez.metronom.rest.RouteResource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;

/**
 * Implements {@link AlertResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
@Stateless
public class AlertResourceImpl implements AlertResource {

  @Inject
  private AlertCache alertCache;

  @Inject
  private RouteResource routeResource;

  @Override
  public List<Alert> findAllAlerts() {
    return alertCache.getAlerts();
  }

  @Override
  public List<Alert> findAlertsSince(@PathParam(value = "since") String dateTime) {
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

    if (localDateTime != null) {
      return streamAlerts().filter(d -> d.getCreationDate().isAfter(localDateTime))
        .collect(toList());
    }

    return Collections.emptyList();
  }

  @Override
  public List<Alert> findByText(@PathParam(value = "text") String text) {
    return streamAlerts().filter(d -> d.getMessage().contains(text)).collect(toList());
  }

  @Override
  public List<Alert> findRelevantAlertsForStation(String station) {
    List<Route> routes = routeResource.findByStop(station);

    List<Station> stations = routes.stream().map(Route::getStations).flatMap(l -> l.stream())
      .distinct().collect(toList());

    // Assume that only information that are max. 1 hour old are still relevant
    LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

    return findAllAlerts().stream()
      .filter(alert -> alert.getCreationDate().isAfter(oneHourAgo))
      .filter(alert -> isAlertRelevantForStation(stations, alert))
      .collect(toList());
  }

  private static boolean isAlertRelevantForStation(List<Station> stations, Alert alert) {
    if (alert.getStationStart() != null && alert.getStationEnd() != null) {
      return stations.contains(alert.getStationStart()) && stations.contains(alert.getStationEnd());
    } else if (alert.getStationStart() != null) {
      return stations.contains(alert.getStationStart());
    }

    return false;
  }

  @Override
  public List<Alert> findAlertsWithUnknownStation() {
    return streamAlerts().filter(alert -> alert.getStationEnd() == null && alert.getStationStart() == null)
      .collect(toList());
  }

  private Stream<Alert> streamAlerts() {
    return alertCache.getAlerts().stream();
  }

}
