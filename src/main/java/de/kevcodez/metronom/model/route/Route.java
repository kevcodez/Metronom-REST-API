/**
 * MIT License
 * <p>
 * Copyright (c) 2016 Kevin Grüneberg
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * <p>
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

    public Route(String name) {
        this.name = name;
    }

    public void addTrains(String... trains) {
        this.trains.addAll(Arrays.asList(trains));
    }

    public void addStation(Station station) {
        if (station == null) {
            throw new IllegalArgumentException("station cannot be null");
        }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashName = 0;
        if (name != null) {
            hashName = name.hashCode();
        }

        return prime + hashName;
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
        Route other = (Route) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
