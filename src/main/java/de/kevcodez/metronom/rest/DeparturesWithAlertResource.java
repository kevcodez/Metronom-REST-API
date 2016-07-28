package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.delay.DeparturesWithAlert;

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
public interface DeparturesWithAlertResource {

  /**
   * Finds all departures and their related alert for the given station. The unassigned, but probably relevant alerts
   * will also be included in a separate segment.
   * 
   * @param station station name
   * @return list of departures and their alert
   */
  @GET
  @Path("/station/{station}")
  DeparturesWithAlert findByStation(@PathParam(value = "station") String station);

}
