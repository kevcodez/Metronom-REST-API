package de.kevcodez.metronom.model.delay;

import de.kevcodez.metronom.model.alert.Alert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Departure with a single, optional alert.
 * 
 * @author Kevin Grüneberg
 *
 */
public class DepartureWithAlert {

  @NotNull
  private Departure departure;

  private Alert alert;

  private List<Alert> unassignedAlerts = new ArrayList<>();

  /**
   * Creates a new deparute alert with the given departure and alert.
   * 
   * @param departure departure
   * @param alert optional alert
   */
  public DepartureWithAlert(Departure departure, Alert alert) {
    this.departure = departure;
    this.alert = alert;
  }

  public void addUnassignedAlert(Alert alert) {
    unassignedAlerts.add(alert);
  }

  public List<Alert> getUnassignedAlerts() {
    return Collections.unmodifiableList(unassignedAlerts);
  }

  public Departure getDeparture() {
    return departure;
  }

  public void setDeparture(Departure departure) {
    this.departure = departure;
  }

  public Alert getAlert() {
    return alert;
  }

  public void setAlert(Alert alert) {
    this.alert = alert;
  }

}
