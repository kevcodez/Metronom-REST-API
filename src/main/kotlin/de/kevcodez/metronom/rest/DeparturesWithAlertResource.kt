package de.kevcodez.metronom.rest

import de.kevcodez.metronom.model.alert.Alert
import de.kevcodez.metronom.model.delay.Departure
import de.kevcodez.metronom.model.delay.DeparturesWithAlert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

/**
 * REST resource for departures and their related alert.
 *
 * @author Kevin Grüneberg
 */
@RestController
@RequestMapping(value = ["departure"], produces = ["application/json"])
class DeparturesWithAlertResource @Autowired constructor(
    private val stationDelayResource: StationDelayResource,
    private val alertResource: AlertResource
) {

    /**
     * Finds all departures and their related alert for the given station.
     *
     * @param station station name
     * @return list of departures and their alert
     */
    @GetMapping("/station/{station}")
    fun findByStation(@PathVariable station: String): DeparturesWithAlert? {
        val stationDelay = stationDelayResource.findStationDelayByName(station) ?: return null

        val alerts = alertResource.findRelevantAlertsForStation(station).toMutableList()

        val departuresWithAlert = DeparturesWithAlert()
        for (departure in stationDelay.departures) {
            val alert = findAlert(departure, alerts)

            alerts.remove(alert)

            if (alert != null)
                departuresWithAlert.addDeparture(departure, alert)
        }

        alerts.forEach { departuresWithAlert.addRemainingAlert(it) }

        return departuresWithAlert
    }

    private fun findAlert(departure: Departure, alerts: List<Alert>): Alert? {
        // Matches train number
        val alertsForTrain = alerts.filter { alert -> alert.message.contains(departure.train) }
            .filter { isMaxThreeHoursOld(it) }

        // TODO Besseres matching durch Tests

        return alertsForTrain.firstOrNull()
    }

    private fun isMaxThreeHoursOld(alert: Alert): Boolean {
        val twoHoursAgo = LocalDateTime.now().minusHours(3)

        return alert.dateTime.isAfter(twoHoursAgo)
    }

}
