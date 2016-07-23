package de.kevcodez.metronom.model.station;

/**
 * Contains a start and a target station.
 * 
 * @author Kevin Grüneberg
 *
 */
public class StartAndTargetStation {

  private Station start;
  private Station target;

  /**
   * Creates a new StartStopStation object with the given start and target station.
   * 
   * @param start start station
   * @param target target station
   */
  public StartAndTargetStation(Station start, Station target) {
    this.start = start;
    this.target = target;
  }

  public Station getStart() {
    return start;
  }

  public Station getTarget() {
    return target;
  }

}
