package de.kevcodez.metronom.model.alert;

import java.io.IOException;

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
   * Parses the alert notifications frmo the Metronom SOAP endpoint (see {@link #METRONOM_ALERT_URL}) and puts them in
   * the {@link AlertCache}. This function is executed every 30 seconds.
   */
  @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
  public void parseAlerts() throws IOException {
    alertCache.setAlerts(alertParser.parseAlerts());
  }

}
