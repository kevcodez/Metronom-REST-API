package de.kevcodez.metronom.test.model.alert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import de.kevcodez.metronom.model.station.StartAndTargetStation;
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

  private StationProvider realStationProvider;

  @Parameters(name = "{0}, {1} - {2})")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
      { "Auf der Strecke Cuxhaven/Hamburg und", "Cuxhaven", "Hamburg" },
      { "ME 82118 von Uelzen nach Hamburg an Uelzen", "Uelzen", "Hamburg" },
      { "ME 82819 von Uelzen nach Göttingen ab Uelzen", "Uelzen", "Göttingen" },
      { "Busnotverkehr von Göttingen nach Hannover mit", "Göttingen", "Hannover" },
      { "Zug 82818 von Göttingen nach Hannover leider entfallen", "Göttingen", "Hannover" },
      { "ME 82834 von Göttingen nach Hannover ab Göttingen", "Göttingen", "Hannover" },
      { "von Hannover (planm. ab 20:33 Uhr) nach Goettingen: Fahrgaeste, die in Goettingen Anschluesse brauchen, koennen ICE 1087 (Abfahrt Hannover 21:01 Uhr) bis Goettingen",
        "Hannover", "Göttingen" },
      { "von ME 81644 von Uelzen (planmäßige Abfahrt: 00:05 Uhr) nach Hamburg um", "Uelzen", "Hamburg" },
      { "Wegen Erkrankung des Lokführer entfällt heute leider ME 82834 von Göttingen nach Hannover ab Göttingen(planm.Abfahrt 21:07 Uhr). Die Fahrgäste nutzen bitte den Folgetakt.",
        "Göttingen", "Hannover" },
      { "Strecke Cuxhaven - Hamburg", "Cuxhaven", "Hamburg" },
      { "in Cuxhaven, Fahrgäste nach Hamburg", "Cuxhaven", "Hamburg" },
      { "ab Hamburg (planmäßige Abfahrt 10:09 Uhr) in Richtung Cuxhaven", "Hamburg", "Cuxhaven" },
      { "zurzeit in Cuxhaven, die Weiterfahrt Richtung Hamburg", "Cuxhaven", "Hamburg" },
      { "82115 nach Cuxhaven noch in Hamburg", "Hamburg", "Cuxhaven" },
      { "Strecke zwischen Cuxhaven und Hamburg", "Cuxhaven", "Hamburg" },
      { "nach Cuxhaven ab Hamburg", "Hamburg", "Cuxhaven" },
      { "hinter Cuxhaven, die Fahrt nach Hamburg", "Cuxhaven", "Hamburg" },
      { "zwischen Hamburg Hbf und Hamburg Harburg ist aufgehoben", "Hamburg", "Harburg" },
      { "zwischen Hamburg Hbf und Hamburg-Harburg ist aufgehoben", "Hamburg", "Harburg" },
      { "Abfahrt von 82815 Uelzen nach Hannover voraussichtlich", "Uelzen", "Hannover" },
      { "Die Störung an der Strecke verursacht zur Zeit Verspätungen zwischen 5-10 Minuten auf der Strecke Cuxhaven nach Hamburg",
        "Cuxhaven", "Hamburg" },
      { " Wegen Aufgrund von Fussballfans steht 82129 von Hamburg nach Uelzen derzeit in Harburg.", "Hamburg",
        "Uelzen" },
      { "Wegen einer Stellwerksstörung verkehrt 82817 von Hannover nach Göttingen ab Vinnhorst", "Hannover",
        "Göttingen" },
      {"Die Streckensperrung Cuxhaven - Stade/ Hamburg-Stade aufgrund", "Cuxhaven", "Stade"}
    });
  }

  @Before
  public void init() {
    realStationProvider = new StationProvider();
    realStationProvider.constructStations();

    MockitoAnnotations.initMocks(this);
    Mockito.when(stationProvider.findStationByName(Mockito.anyString()))
      .then(i -> realStationProvider.findStationByName((String) i.getArgument(0)));
    Mockito.when(stationProvider.getStations()).thenReturn(realStationProvider.getStations());
    
    
    stationFinder.init();

  }

  @Test
  public void shouldFindStartAndTargetStation() {
    StartAndTargetStation startStopStation = stationFinder.findStartAndTarget(text);

    assertThat(startStopStation, is(notNullValue()));
    assertThat(startStopStation.getStart().getName(), is(expectedStart));
    assertThat(startStopStation.getTarget().getName(), is(expectedTarget));
  }

}
