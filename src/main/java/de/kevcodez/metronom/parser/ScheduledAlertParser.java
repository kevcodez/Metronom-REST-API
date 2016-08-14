/**
 * MIT License
 * 
 * Copyright (c) 2016 Kevin Grüneberg
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package de.kevcodez.metronom.parser;

import de.kevcodez.metronom.provider.AlertCache;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Class to parse alert notifications every minute (scheduled job).
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
@Startup
public class ScheduledAlertParser {

  @Inject
  private AlertParser alertParser;

  @Inject
  private AlertCache alertCache;

  /**
   * Parse alerts once on startup.
   */
  @PostConstruct
  public void onPostConstruct() {
    parseAlerts();
  }

  /**
   * Parses the alert notifications from the Metronom SOAP endpoint and puts them in the {@link AlertCache}. This
   * function is executed every minute.
   */
  @Schedule(second = "*/60", minute = "*", hour = "*", persistent = false)
  public void parseAlerts() {
    alertParser.parseAlerts().forEach(alertCache::addAlert);
  }

}
