package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.stop.Station;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST resource that provides stations.
 * 
 * @author Kevin Grüneberg
 *
 */
@Path("station")
@Produces({ MediaType.APPLICATION_JSON })
public interface StationResource {

  /**
   * Finds all stations.
   * 
   * @return list of all stations
   */
  @GET
  List<Station> findAll();

  /**
   * Finds the station with the given name.
   * 
   * @param name name to match
   * @return matching station, or null
   */
  @GET
  @Path("name/{name}")
  Station findByName(@PathParam(value = "name") String name);

  /**
   * Finds the station with the given code.
   * 
   * @param code code to match
   * @return matching station, or null
   */
  @GET
  @Path("code/{code}")
  Station findByCode(@PathParam(value = "code") String code);

}
