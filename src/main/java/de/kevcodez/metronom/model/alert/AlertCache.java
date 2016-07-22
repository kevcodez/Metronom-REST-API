package de.kevcodez.metronom.model.alert;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

/**
 * Singleton to cache alert notifications.
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class AlertCache {

  private List<Alert> alerts;

  public void setAlerts(List<Alert> alerts) {
    this.alerts = alerts;
  }

  public List<Alert> getAlerts() {
    if (alerts == null) {
      alerts = new ArrayList<>();
    }

    return alerts;
  }

}
