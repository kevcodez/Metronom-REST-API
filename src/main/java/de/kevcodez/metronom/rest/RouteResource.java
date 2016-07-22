package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.route.Route;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST resource that provides Metronom routes.
 * 
 * @author Kevin Grüneberg
 *
 */
@Path("route")
@Produces({ MediaType.APPLICATION_JSON })
public interface RouteResource {

  /**
   * Finds all routes.
   * 
   * @return list of all routes
   */
  @GET
  List<Route> findAllRoutes();

  /**
   * Finds a list of routes that contain the given stop.
   * 
   * @param stop stop to search for
   * @return list of matching routes
   */
  @GET
  @Path("stop/{stop}")
  List<Route> findByStop(@PathParam(value = "stop") String stop);

  /**
   * Finds a route that matches the given name.
   * 
   * @param name name to match
   * @return matching route, or null
   */
  @GET
  @Path("name/{name}")
  Route findByName(@PathParam(value = "name") String name);

  /**
   * Finds a route with the given start and stop point.
   * 
   * @param start start point to search for
   * @param stop stop point to search for
   * @return list of matching routes
   */
  @GET
  @Path("{start}/to/{stop}")
  List<Route> findByStartAndStop(@PathParam(value = "start") String start,
    @PathParam(value = "stop") String stop);

}
