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
