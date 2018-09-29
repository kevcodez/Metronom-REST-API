package de.kevcodez.metronom.test.model.route;

import de.kevcodez.metronom.model.route.Route;
import de.kevcodez.metronom.provider.RouteProvider;
import de.kevcodez.metronom.provider.StationProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteProviderTest {

  @InjectMocks
  private RouteProvider routeProvider;

  @Mock
  private StationProvider stationProvider;

  private StationProvider realStationProvider;

  @Before
  public void setup() {
    realStationProvider = new StationProvider();
    realStationProvider.constructStations();

    Mockito.when(stationProvider.findStationByName(Mockito.anyString()))
      .then(i -> realStationProvider.findStationByName((String) i.getArgument(0)));

    routeProvider.constructRoutes();
  }

  @Test
  public void shouldProvideRoutes() {
    List<Route> routes = routeProvider.getRoutes();

    assertThat(routes.size(), greaterThan(0));
  }

  @Test
  public void shouldProvideEqualRoutes() {
    List<Route> routes = routeProvider.getRoutes();

    Route firstRoute = routes.get(0);
    Route secondRoute = routes.get(1);

    assertThat(firstRoute, is(firstRoute));
    assertThat(firstRoute, not(secondRoute));
    assertThat(firstRoute.hashCode(), not(secondRoute.hashCode()));
  }

}
