package de.kevcodez.metronom.model.alert;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * Class to parse alert notifications every X seconds (scheduled job).
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class ScheduledAlertParser {

  @Inject
  private AlertParser alertParser;

  @Inject
  private AlertCache alertCache;

  /**
   * Parses the alert notifications frmo the Metronom SOAP endpoint and puts them in the {@link AlertCache}. This
   * function is executed every 30 seconds.
   */
  @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
  public void parseAlerts() {
    alertCache.setAlerts(alertParser.parseAlerts());
  }

}
