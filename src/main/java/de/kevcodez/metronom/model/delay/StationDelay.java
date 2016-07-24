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

  private List<Departure> departures = new ArrayList<>();

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
   * Adds the given depature to the list.
   * 
   * @param depature departure to add
   */
  public void addDeparture(Departure depature) {
    departures.add(depature);
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

  public List<Departure> getDepartures() {
    return Collections.unmodifiableList(departures);
  }

  @Override
  public String toString() {
    return "StationDelay [station=" + station + ", time=" + time + ", departures=" + departures + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int hashStation = 0;
    if (station != null) {
      hashStation = station.hashCode();
    }

    return prime + hashStation;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    StationDelay other = (StationDelay) obj;
    if (station == null) {
      if (other.station != null) {
        return false;
      }
    } else if (!station.equals(other.station)) {
      return false;
    }
    return true;
  }

}
