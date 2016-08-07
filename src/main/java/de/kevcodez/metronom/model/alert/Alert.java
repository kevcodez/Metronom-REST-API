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
package de.kevcodez.metronom.model.alert;

import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.rest.adapter.LocalDateTimeAdapter;
import de.kevcodez.metronom.rest.adapter.LocalTimeAdapter;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Class that contains all relevant information for a single alert notification from the Metronom website.
 * 
 * @author Kevin Grüneberg
 *
 */
public class Alert {

  private String id;

  private String message;

  private LocalDateTime creationDate;

  private Station stationStart;

  private Station stationEnd;

  private LocalTime plannedDeparture;

  /**
   * Creates a new alert notification with the given ID, text and creation date.
   * 
   * @param id unique ID
   * @param message text message
   * @param creationDate creation date
   * @param plannedDeparture planned departure as time
   */
  public Alert(String id, String message, LocalDateTime creationDate, LocalTime plannedDeparture) {
    this.id = id;
    this.message = message;
    this.creationDate = creationDate;
    this.plannedDeparture = plannedDeparture;
  }

  /**
   * Gets the unique ID.
   * 
   * @return unique ID
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the unique ID.
   * 
   * @param id unique ID
   */
  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public Station getStationStart() {
    return stationStart;
  }

  public void setStationStart(Station stationStart) {
    this.stationStart = stationStart;
  }

  public Station getStationEnd() {
    return stationEnd;
  }

  public void setStationEnd(Station stationEnd) {
    this.stationEnd = stationEnd;
  }

  @XmlJavaTypeAdapter(LocalTimeAdapter.class)
  public LocalTime getPlannedDeparture() {
    return plannedDeparture;
  }

  public void setPlannedDeparture(LocalTime plannedDeparture) {
    this.plannedDeparture = plannedDeparture;
  }

  @Override
  public String toString() {
    return "Alert [id=" + id + ", message=" + message + ", creationDate=" + creationDate + ", stationStart="
      + stationStart + ", stationEnd=" + stationEnd + ", plannedDeparture=" + plannedDeparture + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int hashId = 0;
    if (id != null) {
      hashId = id.hashCode();
    }

    return prime + hashId;
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
    Alert other = (Alert) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

}
