/**
 * MIT License
 * <p>
 * Copyright (c) 2016 Kevin Grüneberg
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.route.Route;
import de.kevcodez.metronom.provider.RouteProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static de.kevcodez.metronom.provider.StationProvider.alternativeNameMatches;
import static de.kevcodez.metronom.provider.StationProvider.stationNameMatches;
import static java.util.stream.Collectors.toList;

/**
 * REST resource that provides Metronom routes.
 *
 * @author Kevin Grüneberg
 */
@RestController
@RequestMapping(value = "route", produces = "application/json")
public class RouteResource {

    @Autowired
    private RouteProvider routeProvider;

    @GetMapping
    public List<Route> findAllRoutes() {
        return routeProvider.getRoutes();
    }

    /**
     * Finds a list of routes that contain the given stop.
     *
     * @param stop stop to search for
     * @return list of matching routes
     */
    @GetMapping("stop/{stop}")
    public List<Route> findByStop(@PathVariable(value = "stop") String stop) {
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

    /**
     * Finds a route that matches the given name.
     *
     * @param name name to match
     * @return matching route, or null
     */
    @GetMapping("name/{name}")
    public Route findByName(@PathVariable(value = "name") String name) {
        return routeProvider.getRoutes().stream()
                .filter(r -> name.equalsIgnoreCase(r.getName()))
                .findFirst().orElse(null);
    }

    /**
     * Finds a route with the given start and stop point.
     *
     * @param start start point to search for
     * @param stop  stop point to search for
     * @return list of matching routes
     */
    @GetMapping("{start}/to/{stop}")
    public List<Route> findByStartAndStop(@PathVariable(value = "start") String start,
                                          @PathVariable(value = "stop") String stop) {
        return routeProvider.getRoutes().stream()
                .filter(r -> hasStation(r, start))
                .filter(r -> hasStation(r, stop))
                .collect(toList());
    }

}
