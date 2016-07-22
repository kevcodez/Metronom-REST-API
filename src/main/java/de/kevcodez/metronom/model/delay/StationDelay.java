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
package de.kevcodez.metronom.model.delay;

import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.rest.adapter.LocalTimeAdapter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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

  /**
   * Creates a new station delay with the given station and time.
   * 
   * @param station station
   * @param time time
   */
  public StationDelay(Station station, LocalTime time) {
    this.station = station;
    this.time = time;
  }

  /**
   * Adds the given delayed depature to the list.
   * 
   * @param delayedDepature delayed departure to add
   */
  public void addDeparture(DelayedDeparture delayedDepature) {
    departures.add(delayedDepature);
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

    return Collections.unmodifiableList(departures);
  }

}
