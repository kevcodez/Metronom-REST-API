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

import de.kevcodez.metronom.model.stop.Station;
import de.kevcodez.metronom.rest.adapter.LocalTimeAdapter;

import java.time.LocalTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Contains the possible delay, target station, train number and time of a departure.
 * 
 * @author Kevin Grüneberg
 *
 */
public class DelayedDeparture {

  private LocalTime time;

  private String train;

  private Station targetStation;

  private int delayInMinutes;

  public DelayedDeparture(String train, Station targetStation, LocalTime time, int delayInMinutes) {
    this.train = train;
    this.targetStation = targetStation;
    this.time = time;
    this.delayInMinutes = delayInMinutes;
  }

  @XmlJavaTypeAdapter(LocalTimeAdapter.class)
  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public String getTrain() {
    return train;
  }

  public void setTrain(String train) {
    this.train = train;
  }

  public Station getTargetStation() {
    return targetStation;
  }

  public void setTargetStation(Station targetStation) {
    this.targetStation = targetStation;
  }

  public int getDelayInMinutes() {
    return delayInMinutes;
  }

  public void setDelayInMinutes(int delayInMinutes) {
    this.delayInMinutes = delayInMinutes;
  }

}
