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

      departuresWithAlert.add(new DepartureWithAlert(departure, alert));
    }

    return departuresWithAlert;
  }

  private Alert findAlert(Departure departure, List<Alert> alerts) {
    // TODO Auto-generated method stub
    return null;
  }

}
