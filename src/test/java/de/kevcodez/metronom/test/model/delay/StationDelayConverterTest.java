package de.kevcodez.metronom.test.model.delay;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.kevcodez.metronom.model.delay.Departure;
import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.model.delay.StationDelayConverter;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.model.station.StationProvider;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Tests {@link StationDelayConverter}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class StationDelayConverterTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @InjectMocks
  private StationDelayConverter stationDelayConverter;

  @Mock
  private StationProvider stationProvider;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

    mockStation("Cuxhaven");
    mockStation("Hamburg");
    mockStation("Rotenburg");
  }

  @Test
  public void shouldConvertJsonDepartures() throws IOException {
    String json = "{'name':'Cuxhaven','stand':'22:44','abfahrt':[{'zeit':'23:15','zug':'81942','ziel':'Hamburg','prognose':'p\u00fcnktlich','prognosemin':'0'},{'zeit':'00:15','zug':'81944','ziel':'Rotenburg','prognose':'p\u00fcnktlich','prognosemin':'5'}]}"
      .replaceAll("'", "\"");

    Station station = stationProvider.findStationByName("Cuxhaven");
    StationDelay stationDelay = stationDelayConverter.convert(station, objectMapper.readTree(json));

    assertThat(stationDelay, is(notNullValue()));
    assertThat(stationDelay.getDepartures().size(), is(2));

    Departure firstDeparture = stationDelay.getDepartures().get(0);
    assertThat(firstDeparture.getTime().toString(), is("23:15"));
    assertThat(firstDeparture.getTrain(), is("81942"));
    assertThat(firstDeparture.getTargetStation().getName(), is("Hamburg"));
    assertThat(firstDeparture.getDelayInMinutes(), is(0));

    Departure secondDeparture = stationDelay.getDepartures().get(1);
    assertThat(secondDeparture.getTime().toString(), is("00:15"));
    assertThat(secondDeparture.getTrain(), is("81944"));
    assertThat(secondDeparture.getTargetStation().getName(), is("Rotenburg"));
    assertThat(secondDeparture.getDelayInMinutes(), is(5));
  }

  @Test
  public void shouldParseTrack() throws IOException {
    String json = "{'name':'Cuxhaven','stand':'22:10','abfahrt':[{'zeit':'22:33','zug':'82835','ziel':'Hamburg','prognose':'p\u00fcnktlich, heute Gleis 8','prognosemin':'0','gleiswechsel':'8B'}]}"
      .replaceAll("'", "\"");

    Station station = stationProvider.findStationByName("Cuxhaven");
    StationDelay stationDelay = stationDelayConverter.convert(station, objectMapper.readTree(json));

    assertThat(stationDelay, is(notNullValue()));
    assertThat(stationDelay.getDepartures().size(), is(1));

    assertThat(stationDelay.getDepartures().get(0).getTrack(), is("8"));
  }

  private void mockStation(String station) {
    Mockito.when(stationProvider.findStationByName(station))
      .thenReturn(new Station(station, station));
  }

}
