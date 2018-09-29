package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.model.route.Route;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.provider.AlertCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "alert", produces = "application/json")
public class AlertResource {

    @Autowired
    private AlertCache alertCache;

    @Autowired
    private RouteResource routeResource;

    @GetMapping
    public List<Alert> findAllAlerts() {
        return alertCache.getAlerts();
    }


    /**
     * Finds all alerts since the given date time. The date time must be in ISO-8601 format, otherwise an empty collection
     * will be returned.
     *
     * @param dateTime minimum date time
     * @return matching alerts
     */
    @GetMapping("since/{since}")
    public List<Alert> findAlertsSince(@PathVariable(value = "since") String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

        return streamAlerts().filter(d -> d.getDateTime().isAfter(localDateTime))
                .collect(toList());
    }

    /**
     * Finds all alerts that contains the given text.
     *
     * @param text text to search for
     * @return matching alerts
     */
    @GetMapping("contains/{text}")
    public List<Alert> findByText(@PathVariable(value = "text") String text) {
        return streamAlerts().filter(d -> d.getMessage().contains(text)).collect(toList());
    }

    /**
     * Finds the alerts relevant for the station with the given name that were created during the last hour.
     *
     * @param station station name
     * @return relevant alerts
     */
    @GetMapping("station/{station}")
    public List<Alert> findRelevantAlertsForStation(@PathVariable(value = "station") String station) {
        List<Route> routes = routeResource.findByStop(station);

        List<Station> stations = routes.stream().map(Route::getStations).flatMap(Collection::stream)
                .distinct().collect(toList());

        // Assume that only information that are max. 1 1/2 hour old are still relevant
        LocalTime oneAndAHalfHoursAgo = LocalTime.now().minusHours(1).minusMinutes(30);

        return findAllAlerts().stream()
                .filter(alert -> alert.getPlannedDeparture() != null && alert.getPlannedDeparture().isAfter(oneAndAHalfHoursAgo))
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

    /**
     * Finds the alerts where the system could not assign a start or stop station.
     *
     * @return list of alerts
     */
    @GetMapping("unassigned")
    public List<Alert> findAlertsWithUnknownStation() {
        return streamAlerts().filter(alert -> alert.getStationEnd() == null && alert.getStationStart() == null)
                .collect(toList());
    }

    private Stream<Alert> streamAlerts() {
        return alertCache.getAlerts().stream();
    }

}
