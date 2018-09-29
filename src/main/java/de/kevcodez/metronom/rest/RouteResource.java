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
