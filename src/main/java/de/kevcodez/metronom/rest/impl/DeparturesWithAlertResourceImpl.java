package de.kevcodez.metronom.rest.impl;

import static java.util.stream.Collectors.toList;

import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.model.delay.Departure;
import de.kevcodez.metronom.model.delay.DeparturesWithAlert;
import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.rest.AlertResource;
import de.kevcodez.metronom.rest.DeparturesWithAlertResource;
import de.kevcodez.metronom.rest.StationDelayResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Implements {@link DeparturesWithAlertResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
@Stateless
public class DeparturesWithAlertResourceImpl implements DeparturesWithAlertResource {

  @Inject
  private StationDelayResource stationDelayResource;

  @Inject
  private AlertResource alertResource;

  @Override
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
      .filter(DeparturesWithAlertResourceImpl::isMaxTwoHoursOld).collect(toList());
    alertsWithoutStation.forEach(departuresWithAlert::addRemainingAlert);

    return departuresWithAlert;
  }

  private static Alert findAlert(Departure departure, List<Alert> alerts) {
    // Matches train number
    List<Alert> alertsForTrain = alerts.stream().filter(alert -> alert.getMessage().contains(departure.getTrain()))
      .collect(Collectors.toList());

    // Max 2 hours old
    alertsForTrain = alertsForTrain.stream().filter(DeparturesWithAlertResourceImpl::isMaxTwoHoursOld)
      .collect(toList());

    // TODO Besseres matching durch Tests

    if (!alertsForTrain.isEmpty()) {
      return alertsForTrain.get(0);
    }

    return null;
  }

  private static boolean isMaxTwoHoursOld(Alert alert) {
    LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);

    return alert.getCreationDate().isAfter(twoHoursAgo);
  }

}
