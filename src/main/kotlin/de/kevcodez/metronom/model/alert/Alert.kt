package de.kevcodez.metronom.model.alert

import de.kevcodez.metronom.model.station.Station

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Class that contains all relevant information for a single alert notification from the Metronom website.
 *
 * @author Kevin Grüneberg
 */
data class Alert(
    val message: String,
    val creationDate: LocalDate,
    val plannedDeparture: LocalTime? = null,
    val timeFrom: LocalTime,
    val timeTo: LocalTime,
    val stationStart: Station,
    val stationEnd: Station
) {

    val dateTime: LocalDateTime get() = LocalDateTime.of(creationDate, timeFrom)

}
