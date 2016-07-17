package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.parser.Delay;

import java.util.List;

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
public interface DelayResource {

  /**
   * Finds all delays.
   * 
   * @return list of delays
   */
  @GET
  List<Delay> findAllDelays();

  /**
   * Finds all delays since the given date time. The date time must be in ISO-8601 format, otherwise an empty collection
   * will be returned.
   * 
   * @param dateTime minimum date time
   * @return matching delays
   */
  @GET
  @Path("since/{since}")
  List<Delay> findDelaysSince(@PathParam(value = "since") String dateTime);

  /**
   * Finds all delays that contains the given text.
   * 
   * @param text text to search for
   * @return matching delays
   */
  @GET
  @Path("contains/{text}")
  List<Delay> findByText(@PathParam(value = "text") String text);

}
