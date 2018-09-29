package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.model.delay.Departure;
import de.kevcodez.metronom.model.delay.DeparturesWithAlert;
import de.kevcodez.metronom.model.delay.StationDelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * REST resource for departures and their related alert.
 *
 * @author Kevin Grüneberg
 */
@RestController
@RequestMapping(value = "departure", produces = "application/json")
public class DeparturesWithAlertResource {

    @Autowired
    private StationDelayResource stationDelayResource;

    @Autowired
    private AlertResource alertResource;

    /**
     * Finds all departures and their related alert for the given station. The unassigned, but probably relevant alerts
     * will also be included in a separate segment.
     *
     * @param station station name
     * @return list of departures and their alert
     */
    @GetMapping("/station/{station}")
    public DeparturesWithAlert findByStation(String station) {
        StationDelay stationDelay = stationDelayResource.findStationDelayByName(station);

        if (stationDelay == null) {
            return null;
        }

        List<Alert> alerts = alertResource.findRelevantAlertsForStation(station);

        DeparturesWithAlert departuresWithAlert = new DeparturesWithAlert();
        for (Departure departure : stationDelay.getDepartures()) {
            Alert alert = findAlert(departure, alerts);

            alerts.remove(alert);

            departuresWithAlert.addDeparture(departure, alert);
        }

        // Add remaining, unassigned alerts
        alerts.forEach(departuresWithAlert::addRemainingAlert);

        // Adds alerts that have no start/stop station to the list of unassigned alerts
        List<Alert> alertsWithoutStation = alertResource.findAlertsWithUnknownStation().stream()
                .filter(DeparturesWithAlertResource::isMaxThreeHoursOld).collect(toList());
        alertsWithoutStation.forEach(departuresWithAlert::addRemainingAlert);

        return departuresWithAlert;
    }

    private static Alert findAlert(Departure departure, List<Alert> alerts) {
        // Matches train number
        List<Alert> alertsForTrain = alerts.stream().filter(alert -> alert.getMessage().contains(departure.getTrain()))
                .collect(Collectors.toList());

        // Max 3 hours old
        alertsForTrain = alertsForTrain.stream().filter(DeparturesWithAlertResource::isMaxThreeHoursOld)
                .collect(toList());

        // TODO Besseres matching durch Tests

        if (!alertsForTrain.isEmpty()) {
            return alertsForTrain.get(0);
        }

        return null;
    }

    private static boolean isMaxThreeHoursOld(Alert alert) {
        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(3);

        return alert.getDateTime().isAfter(twoHoursAgo);
    }

}
