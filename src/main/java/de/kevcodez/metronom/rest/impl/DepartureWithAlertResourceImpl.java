package de.kevcodez.metronom.rest.impl;

import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.model.delay.Departure;
import de.kevcodez.metronom.model.delay.DepartureWithAlert;
import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.rest.AlertResource;
import de.kevcodez.metronom.rest.DepartureWithAlertResource;
import de.kevcodez.metronom.rest.StationDelayResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Implements {@link DepartureWithAlertResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
@Stateless
public class DepartureWithAlertResourceImpl implements DepartureWithAlertResource {

  @Inject
  private StationDelayResource stationDelayResource;

  @Inject
  private AlertResource alertResource;

  @Override
  public List<DepartureWithAlert> findByStation(String station) {
    StationDelay stationDelay = stationDelayResource.findStationDelayByName(station);
    List<Alert> alerts = alertResource.findRelevantAlertsForStation(station);

    List<DepartureWithAlert> departuresWithAlert = new ArrayList<>();
    for (Departure departure : stationDelay.getDepartures()) {
      Alert alert = findAlert(departure, alerts);

      alerts.remove(alert);

      departuresWithAlert.add(new DepartureWithAlert(departure, alert));
    }

    return departuresWithAlert;
  }

  private static Alert findAlert(Departure departure, List<Alert> alerts) {
    List<Alert> alertsForTrain = alerts.stream().filter(alert -> alert.getMessage().contains(departure.getTrain()))
      .collect(Collectors.toList());

    // TODO Zeitlich eingrenzen / Besseres matching

    if (!alertsForTrain.isEmpty()) {
      return alertsForTrain.get(0);
    }

    return null;
  }

}
