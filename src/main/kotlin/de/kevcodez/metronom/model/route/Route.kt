package de.kevcodez.metronom.model.route

import de.kevcodez.metronom.model.station.Station
import java.util.*

/**
 * Represents a single route with a name (*-Takt), a list of trains and a list of stops.
 *
 * @author Kevin Grüneberg
 */
data class Route(
    val name: String
) {

    val trains: MutableList<String> = ArrayList()
    val stations: MutableList<Station> = ArrayList()

    fun addTrains(vararg trains: String) {
        this.trains.addAll(Arrays.asList(*trains))
    }

    fun addStation(station: Station?) {
        if (station == null) {
            throw IllegalArgumentException("station cannot be null")
        }

        stations.add(station)
    }

}
