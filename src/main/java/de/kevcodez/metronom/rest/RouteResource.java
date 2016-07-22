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
