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
package de.kevcodez.metronom.model.route;

import de.kevcodez.metronom.model.station.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single route with a name (*-Takt), a list of trains and a list of stops.
 * 
 * @author Kevin Grüneberg
 *
 */
public class Route {

  private String name;

  private List<String> trains = new ArrayList<>();

  private List<Station> stations = new ArrayList<>();

  /**
   * Creates a new route with the given name.
   * 
   * @param name route name
   */
  public Route(String name) {
    this.name = name;
  }

  /**
   * Adds the given array of trains to the list of trains.
   * 
   * @param trains trains to add
   */
  public void addTrains(String... trains) {
    this.trains.addAll(Arrays.asList(trains));
  }

  /**
   * Adds the given station to the list of stations.
   * 
   * @param station station to add
   */
  public void addStation(Station station) {
    stations.add(station);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTrains() {
    return Collections.unmodifiableList(trains);
  }

  public List<Station> getStations() {
    return Collections.unmodifiableList(stations);
  }

  @Override
  public String toString() {
    return "Route [name=" + name + ", trains=" + trains + ", stations=" + stations + "]";
  }

}
