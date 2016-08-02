package de.kevcodez.metronom.model.delay;

import de.kevcodez.metronom.model.alert.Alert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Departure with a single, optional alert.
 * 
 * @author Kevin Grüneberg
 *
 */
public class DeparturesWithAlert {

  private List<SingleDeparture> departures = new ArrayList<>();

  private List<Alert> remainingAlerts = new ArrayList<>();

  /**
   * Adds the given departure and alert to the list of departures. The given alert refers to the given departure.
   * 
   * @param departure departure
   * @param alert alert that refers to the departure
   */
  public void addDeparture(Departure departure, Alert alert) {
    departures.add(new SingleDeparture(departure, alert));
  }

  /**
   * Adds the given alert to the list of remaining alerts.
   * 
   * @param alert alert to add
   */
  public void addRemainingAlert(Alert alert) {
    remainingAlerts.add(alert);
  }

  public List<SingleDeparture> getDepartures() {
    return Collections.unmodifiableList(departures);
  }

  public List<Alert> getRemainingAlerts() {
    return Collections.unmodifiableList(remainingAlerts);
  }

  class SingleDeparture {

    private Departure departure;

    private Alert alert;

    /**
     * Creates a new deparute alert with the given departure and alert.
     * 
     * @param departure departure
     * @param alert optional alert
     */
    SingleDeparture(Departure departure, Alert alert) {
      this.departure = departure;
      this.alert = alert;
    }

    public Departure getDeparture() {
      return departure;
    }

    public Alert getAlert() {
      return alert;
    }

  }

}
