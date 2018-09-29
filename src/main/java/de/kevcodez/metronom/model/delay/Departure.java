package de.kevcodez.metronom.model.delay;

import de.kevcodez.metronom.model.station.Station;

import java.time.LocalTime;

/**
 * Contains the possible delay, target station, train number and time of a departure.
 *
 * @author Kevin Grüneberg
 *
 */
public class Departure {

    private LocalTime time;

    private String train;

    private Station targetStation;

    private int delayInMinutes;

    private String track;

    private boolean cancelled;

    public Departure(String train, Station targetStation, LocalTime time, int delayInMinutes) {
        this.train = train;
        this.targetStation = targetStation;
        this.time = time;
        this.delayInMinutes = delayInMinutes;
    }

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

    public void setTrack(String track) {
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String toString() {
        return "Departure [time=" + time + ", train=" + train + ", targetStation=" + targetStation + ", delayInMinutes="
                + delayInMinutes + ", track=" + track + "]";
    }

}
