package de.kevcodez.metronom.model.route;

import de.kevcodez.metronom.model.stop.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single route with a name (*-Takt), a list of trains and a list of stops.
 * 
 * @author Kevin Grüneberg
 *
 */
public class Route {

  private String name;

  private List<String> trains;

  private List<Station> stations;

  public Route(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTrains() {
    if (trains == null) {
      trains = new ArrayList<>();
    }

    return trains;
  }

  public List<Station> getStations() {
    if (stations == null) {
      stations = new ArrayList<>();
    }

    return stations;
  }

}
