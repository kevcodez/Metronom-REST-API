package de.kevcodez.metronom.model.alert;

import de.kevcodez.metronom.model.station.Station;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Class that contains all relevant information for a single alert notification from the Metronom website.
 *
 * @author Kevin Grüneberg
 *
 */
public class Alert {

    private String message;

    private LocalDate creationDate;

    private LocalTime timeFrom;

    private LocalTime timeTo;

    private Station stationStart;

    private Station stationEnd;

    private LocalTime plannedDeparture;

    public Alert(String message, LocalDate creationDate, LocalTime plannedDeparture) {
        this.message = message;
        this.creationDate = creationDate;
        this.plannedDeparture = plannedDeparture;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(getCreationDate(), getTimeFrom());
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
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

    public LocalTime getPlannedDeparture() {
        return plannedDeparture;
    }

    public void setPlannedDeparture(LocalTime plannedDeparture) {
        this.plannedDeparture = plannedDeparture;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    @Override
    public String toString() {
        return "Alert [message=" + message + ", creationDate=" + creationDate + ", stationStart="
                + stationStart + ", stationEnd=" + stationEnd + ", plannedDeparture=" + plannedDeparture + "]";
    }

}
