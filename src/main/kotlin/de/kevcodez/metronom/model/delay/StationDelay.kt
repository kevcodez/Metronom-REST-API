package de.kevcodez.metronom.model.delay

import de.kevcodez.metronom.model.station.Station
import java.time.LocalTime
import java.util.*

/**
 * Represents a list of possible departure with possible delays from a single [Station].
 *
 * @author Kevin Grüneberg
 */
class StationDelay(
    val station: Station,
    val time: LocalTime,
    val departures: MutableList<Departure> = ArrayList()
) {

    fun addDeparture(depature: Departure) {
        departures.add(depature)
    }

    override fun toString(): String {
        return "StationDelay [station=$station, time=$time, departures=$departures]"
    }

}
