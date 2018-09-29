package de.kevcodez.metronom.model.delay;

import de.kevcodez.metronom.model.station.Station;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public StationDelay(Station station, LocalTime time) {
        this.station = station;
        this.time = time;
    }

    public void addDeparture(Departure depature) {
        departures.add(depature);
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

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
