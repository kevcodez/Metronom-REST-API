package de.kevcodez.metronom.test.rest;

import de.kevcodez.metronom.model.route.Route;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.provider.RouteProvider;
import de.kevcodez.metronom.rest.RouteResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteResourceTest {

  @Mock
  private RouteProvider routeProvider;

  @InjectMocks
  private RouteResource routeResource;

  @Before
  public void setup() {
    Mockito.when(routeProvider.getRoutes()).thenReturn(constructSampleRoutes());
  }

  @Test
  public void shouldFindAllRoutes() {
    List<Route> routes = routeResource.findAllRoutes();
    assertThat(routes.size(), greaterThan(0));
  }

  @Test
  public void shouldFindRouteByName() {
    Route route = routeResource.findByName("Elbe-Takt");

    assertThat(route, notNullValue());
    assertThat(route.getName(), is("Elbe-Takt"));
  }

  @Test
  public void shouldNotFindNonExistingRoute() {
    assertThat(routeResource.findByName("nope"), nullValue());
  }

  @Test
  public void shouldFindRouteByStop() {
    List<Route> route = routeResource.findByStop("Maschen");

    assertThat(route.size(), is(1));

    assertThat(routeHasStation(route, "Maschen"), is(true));
  }

  @Test
  public void shouldFindRouteByStartAndStop() {
    List<Route> route = routeResource.findByStartAndStop("Maschen", "Meckelfeld");

    assertThat(route.size(), is(1));

    assertThat(routeHasStation(route, "Maschen"), is(true));
    assertThat(routeHasStation(route, "Meckelfeld"), is(true));
  }

  private boolean routeHasStation(List<Route> route, String stationName) {
    return route.get(0).getStations().stream().map(Station::getName).anyMatch(stationName::equals);
  }

  private List<Route> constructSampleRoutes() {
    Route route = new Route("Elbe-Takt");
    route.addTrains("RB 31", "RB 3");
    route.addStation(new Station("Maschen", "AMA"));
    route.addStation(new Station("Meckelfeld", "AMDH"));
    route.addStation(new Station("Stelle", "ASTE"));

    List<Route> routes = new ArrayList<>();
    routes.add(route);
    return routes;
  }

}
