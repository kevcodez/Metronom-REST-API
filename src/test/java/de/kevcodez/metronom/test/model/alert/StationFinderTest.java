package de.kevcodez.metronom.test.model.alert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import de.kevcodez.metronom.model.station.StartAndTargetStation;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.model.station.StationFinder;
import de.kevcodez.metronom.model.station.StationProvider;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Tests {@link StationFinder}.
 * 
 * @author Kevin Grüneberg
 *
 */
@RunWith(Parameterized.class)
public class StationFinderTest {

  @Mock
  private StationProvider stationProvider;

  @InjectMocks
  private StationFinder stationFinder;

  @Parameter(value = 0)
  public String text;

  @Parameter(value = 1)
  public String expectedStart;

  @Parameter(value = 2)
  public String expectedTarget;

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
      { "Auf der Strecke Cuxhaven/Hamburg und", "Cuxhaven", "Hamburg" },
      { "ME 82118 von Uelzen nach Hamburg an Uelzen", "Uelzen", "Hamburg" },
      { "ME 82819 von Uelzen nach Göttingen ab Uelzen", "Uelzen", "Göttingen" },
      { "Busnotverkehr von Göttingen nach Hannover mit", "Göttingen", "Hannover" },
      { "Zug 82818 von Göttingen nach Hannover leider entfallen", "Göttingen", "Hannover" }
    });
  }

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

    mockStation("Cuxhaven");
    mockStation("Hamburg");
    mockStation("Göttingen");
    mockStation("Hannover");
    mockStation("Uelzen");
  }

  @Test
  public void shouldFindStartAndTargetStation() {
    StartAndTargetStation startStopStation = stationFinder.findStation(text);

    assertThat(startStopStation, is(notNullValue()));
    assertThat(startStopStation.getStart().getName(), is(expectedStart));
    assertThat(startStopStation.getTarget().getName(), is(expectedTarget));
  }

  private void mockStation(String station) {
    Mockito.when(stationProvider.findStationByName(station))
      .thenReturn(new Station(station, station));
  }
}
