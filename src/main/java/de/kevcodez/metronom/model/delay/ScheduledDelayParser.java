package de.kevcodez.metronom.model.delay;

import java.io.IOException;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * Class to parse delay notifications every X seconds (scheduled job).
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class ScheduledDelayParser {

  @Inject
  private DelayParser delayParser;

  @Inject
  private DelayCache delayCache;

  /**
   * Parses the delay notifications frmo the Metronom SOAP endpoint (see {@link #METRONOM_DELAY_URL}) and puts them in
   * the {@link DelayCache}. This function is executed every 30 seconds.
   */
  @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
  public void parseDelays() throws IOException {
    delayCache.setDelays(delayParser.parseDelays());
  }

}
