package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.delay.StationDelay;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST resource that provides possible delays for a given station.
 * 
 * @author Kevin Grüneberg
 *
 */
@Path("stationDelay")
@Produces({ MediaType.APPLICATION_JSON })
public interface StationDelayResource {

  /**
   * Gets the delay information for the station with the given name.
   * 
   * @param name station name
   * @return station delay information
   */
  @GET
  @Path("{name}")
  StationDelay findStationDelayByName(@PathParam("name") String name);

  /**
   * Gets the delay information for the station with the given code.
   * 
   * @param code station code
   * @return station delay information
   */
  @GET
  @Path("code/{code}")
  StationDelay findStationDelayByCode(@PathParam("code") String code);

}
