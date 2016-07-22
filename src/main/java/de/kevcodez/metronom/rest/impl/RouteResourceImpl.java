package de.kevcodez.metronom.rest.impl;

import static java.util.stream.Collectors.toList;

import de.kevcodez.metronom.model.route.Route;
import de.kevcodez.metronom.model.route.RouteProvider;
import de.kevcodez.metronom.rest.RouteResource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Implements {@link RouteResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
@Stateless
public class RouteResourceImpl implements RouteResource {

  @Inject
  private RouteProvider routeProvider;

  @Override
  public List<Route> findAllRoutes() {
    return routeProvider.getRoutes();
  }

  @Override
  public List<Route> findByStop(String stop) {
    return routeProvider.getRoutes().stream()
      .filter(r -> r.getStops().contains(stop))
      .collect(toList());
  }

  @Override
  public Route findByName(String name) {
    return routeProvider.getRoutes().stream()
      .filter(r -> name.equals(r.getName()))
      .findFirst().orElse(null);
  }

  @Override
  public List<Route> findByStartAndStop(String start, String stop) {
    return routeProvider.getRoutes().stream()
      .filter(r -> r.getStops().contains(start))
      .filter(r -> r.getStops().contains(stop))
      .collect(toList());
  }

}
