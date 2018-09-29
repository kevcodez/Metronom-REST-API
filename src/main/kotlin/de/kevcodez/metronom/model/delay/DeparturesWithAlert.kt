package de.kevcodez.metronom.model.delay

import de.kevcodez.metronom.model.alert.Alert
import java.util.*

/**
 * Departure with a single, optional alert.
 *
 * @author Kevin Grüneberg
 */
data class DeparturesWithAlert(
    val departures: MutableList<SingleDeparture> = ArrayList(),
    val remainingAlerts: MutableList<Alert> = ArrayList()
) {

    /**
     * Adds the given departure and alert to the list of departures. The given alert refers to the given departure.
     *
     * @param departure departure
     * @param alert     alert that refers to the departure
     */
    fun addDeparture(departure: Departure, alert: Alert) {
        departures.add(SingleDeparture(departure, alert))
    }

    fun addRemainingAlert(alert: Alert) {
        remainingAlerts.add(alert)
    }

    inner class SingleDeparture(val departure: Departure, val alert: Alert)

}
