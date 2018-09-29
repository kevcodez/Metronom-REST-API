package de.kevcodez.metronom.model.delay

import de.kevcodez.metronom.model.station.Station

import java.time.LocalTime

/**
 * Contains the possible delay, target station, train number and time of a departure.
 *
 * @author Kevin Grüneberg
 */
class Departure(
    val train: String,
    val targetStation: Station,
    val time: LocalTime,
    val delayInMinutes: Int,
    val track: String? = null,
    val isCancelled: Boolean = false
)