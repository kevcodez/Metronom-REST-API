package de.kevcodez.metronom.rest;

import static java.util.stream.Collectors.toList;

import de.kevcodez.metronom.DelayCache;
import de.kevcodez.metronom.parser.Delay;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST resource that provides delay notifications.
 * 
 * @author Kevin Grüneberg
 *
 */
@Path("delay")
@Produces({ MediaType.APPLICATION_JSON })
public class DelayResource {

  @Inject
  private DelayCache delayCache;

  /**
   * Finds all delays.
   * 
   * @return list of delays
   */
  @GET
  public List<Delay> findAllDelays() {
    return delayCache.getDelays();
  }

  /**
   * Finds all delays since the given date time. The date time must be in ISO-8601 format, otherwise an empty collection
   * will be returned.
   * 
   * @param dateTime minimum date time
   * @return matching delays
   */
  @GET
  @Path("since/{since}")
  public List<Delay> findDelaysSince(@PathParam(value = "since") String dateTime) {
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

    if (localDateTime != null) {
      return streamDelays().filter(d -> d.getCreatedAt().isAfter(localDateTime)).collect(toList());
    }

    return Collections.emptyList();
  }

  /**
   * Finds all delays that contains the given text.
   * 
   * @param text text to search for
   * @return matching delays
   */
  @GET
  @Path("contains/{text}")
  public List<Delay> findByText(@PathParam(value = "text") String text) {
    return streamDelays().filter(d -> d.getText().contains(text)).collect(toList());
  }

  private Stream<Delay> streamDelays() {
    return delayCache.getDelays().stream();
  }

}
