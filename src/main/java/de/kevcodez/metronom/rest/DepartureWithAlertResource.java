package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.delay.DepartureWithAlert;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST resource for departures and their related alert.
 * 
 * @author Kevin Grüneberg
 *
 */
@Path("departure")
@Produces({ MediaType.APPLICATION_JSON })
public interface DepartureWithAlertResource {

  /**
   * Finds all departures and their related alert for the given station.
   * 
   * @param station station name
   * @return list of departures and their alert
   */
  @GET
  @Path("/station/{station}")
  List<DepartureWithAlert> findByStation(@PathParam(value = "station") String station);

}
