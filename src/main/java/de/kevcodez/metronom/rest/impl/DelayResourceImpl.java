package de.kevcodez.metronom.rest.impl;

import static java.util.stream.Collectors.toList;

import de.kevcodez.metronom.model.delay.Delay;
import de.kevcodez.metronom.model.delay.DelayCache;
import de.kevcodez.metronom.rest.DelayResource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Implements {@link DelayResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
@Path("delay")
@Produces({ MediaType.APPLICATION_JSON })
public class DelayResourceImpl implements DelayResource {

  @Inject
  private DelayCache delayCache;

  @Override
  public List<Delay> findAllDelays() {
    return delayCache.getDelays();
  }

  @Override
  public List<Delay> findDelaysSince(@PathParam(value = "since") String dateTime) {
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

    if (localDateTime != null) {
      return streamDelays().filter(d -> d.getCreationDate().isAfter(localDateTime))
        .collect(toList());
    }

    return Collections.emptyList();
  }

  @Override
  public List<Delay> findByText(@PathParam(value = "text") String text) {
    return streamDelays().filter(d -> d.getMessage().contains(text)).collect(toList());
  }

  private Stream<Delay> streamDelays() {
    return delayCache.getDelays().stream();
  }

}
