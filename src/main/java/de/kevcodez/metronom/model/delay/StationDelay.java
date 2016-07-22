package de.kevcodez.metronom.model.delay;

import de.kevcodez.metronom.model.stop.Station;
import de.kevcodez.metronom.rest.adapter.LocalTimeAdapter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Represents a list of possible departure with possible delays from a single {@link Station}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class StationDelay {

  private Station station;
  private LocalTime time;

  private List<DelayedDeparture> departures;

  public StationDelay(Station station, LocalTime time) {
    this.station = station;
    this.time = time;
  }

  public Station getStation() {
    return station;
  }

  public void setStation(Station station) {
    this.station = station;
  }

  @XmlJavaTypeAdapter(LocalTimeAdapter.class)
  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public List<DelayedDeparture> getDepartures() {
    if (departures == null) {
      departures = new ArrayList<>();
    }

    return departures;
  }

}
