package de.kevcodez.metronom.rest.impl;

import static java.util.stream.Collectors.toList;

import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.model.alert.AlertCache;
import de.kevcodez.metronom.rest.AlertResource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.ws.rs.PathParam;

/**
 * Implements {@link AlertResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class AlertResourceImpl implements AlertResource {

  @Inject
  private AlertCache alertCache;

  @Override
  public List<Alert> findAllAlerts() {
    return alertCache.getAlerts();
  }

  @Override
  public List<Alert> findAlertsSince(@PathParam(value = "since") String dateTime) {
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

    if (localDateTime != null) {
      return streamAlerts().filter(d -> d.getCreationDate().isAfter(localDateTime))
        .collect(toList());
    }

    return Collections.emptyList();
  }

  @Override
  public List<Alert> findByText(@PathParam(value = "text") String text) {
    return streamAlerts().filter(d -> d.getMessage().contains(text)).collect(toList());
  }

  private Stream<Alert> streamAlerts() {
    return alertCache.getAlerts().stream();
  }

}
