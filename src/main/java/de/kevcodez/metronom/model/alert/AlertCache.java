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
package de.kevcodez.metronom.model.alert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton to cache alert notifications.
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class AlertCache {

  private static final Logger LOG = LoggerFactory.getLogger(AlertCache.class);

  private List<Alert> alerts = new ArrayList<>();

  @Inject
  private Event<Alert> newAlert;

  /**
   * Adds the given alert to the list of alerts.
   * 
   * @param alert alert to add
   */
  public void addAlert(Alert alert) {
    if (!alerts.contains(alert)) {
      verifyStartStation(alert);

      newAlert.fire(alert);
      alerts.add(alert);
    }
  }

  public List<Alert> getAlerts() {
    return Collections.unmodifiableList(alerts);
  }

  private void verifyStartStation(Alert alert) {
    if (alert.getStationStart() == null) {
      LOG.warn("start station not found for {}", alert.getMessage());
    }
  }

}
