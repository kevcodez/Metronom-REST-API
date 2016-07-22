package de.kevcodez.metronom.model.route;

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

  private List<String> stops;

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

  public List<String> getStops() {
    if (stops == null) {
      stops = new ArrayList<>();
    }

    return stops;
  }

}
