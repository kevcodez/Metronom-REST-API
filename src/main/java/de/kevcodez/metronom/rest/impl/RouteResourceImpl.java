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

import static de.kevcodez.metronom.model.station.StationProvider.alternativeNameMatches;
import static de.kevcodez.metronom.model.station.StationProvider.stationNameMatches;
import static java.util.stream.Collectors.toList;

import de.kevcodez.metronom.model.route.Route;
import de.kevcodez.metronom.model.route.RouteProvider;
import de.kevcodez.metronom.rest.RouteResource;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Implements {@link RouteResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
@Stateless
public class RouteResourceImpl implements RouteResource {

  @Inject
  private RouteProvider routeProvider;

  @Override
  public List<Route> findAllRoutes() {
    return routeProvider.getRoutes();
  }

  @Override
  public List<Route> findByStop(String stop) {
    if (stop == null) {
      return Collections.emptyList();
    }

    return routeProvider.getRoutes().stream()
      .filter(r -> hasStation(r, stop))
      .collect(toList());
  }

  private static boolean hasStation(Route route, String stationName) {
    return route.getStations().stream()
      .anyMatch(station -> stationNameMatches(stationName, station) || alternativeNameMatches(stationName, station));
  }

  @Override
  public Route findByName(String name) {
    return routeProvider.getRoutes().stream()
      .filter(r -> name.equalsIgnoreCase(r.getName()))
      .findFirst().orElse(null);
  }

  @Override
  public List<Route> findByStartAndStop(String start, String stop) {
    return routeProvider.getRoutes().stream()
      .filter(r -> hasStation(r, start))
      .filter(r -> hasStation(r, stop))
      .collect(toList());
  }

}
