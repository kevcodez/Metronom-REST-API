package de.kevcodez.metronom.test.model.station;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.model.station.StationProvider;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StationProviderTest {

  private StationProvider stationProvider;

  @Before
  public void before() {
    stationProvider = new StationProvider();
    stationProvider.constructStations();
  }

  @Test
  public void shouldProvideStations() {
    List<Station> stations = stationProvider.getStations();
    assertThat(stations.size(), greaterThan(0));
  }

  @Test
  public void shouldFindStationByCode() {
    Station station = stationProvider.findStationByCode("AMA");

    assertThat(station, notNullValue());
    assertThat(station.getName(), is("Maschen"));
    assertThat(station.getCode(), is("AMA"));
  }

  @Test
  public void shouldFindStationByName() {
    Station station = stationProvider.findStationByName("Harburg");

    assertThat(station, notNullValue());
    assertThat(station.getName(), is("Harburg"));
  }

  @Test
  public void shouldFindStationByAlternativeName() {
    Station station = stationProvider.findStationByName("Hamburg-Harburg");

    assertThat(station, notNullValue());
    assertThat(station.getName(), is("Harburg"));
  }

  @Test
  public void shouldHaveEqualStations() {
    Station stationByCode = stationProvider.findStationByCode("AMA");
    Station stationByName = stationProvider.findStationByName("Maschen");

    assertThat(stationByCode, is(stationByName));
    assertThat(stationByCode.hashCode(), is(stationByName.hashCode()));

    Station differentStation = stationProvider.findStationByName("Harburg");
    assertThat(stationByCode, not(differentStation));
    assertThat(stationByCode.hashCode(), not(differentStation.hashCode()));
  }

}
