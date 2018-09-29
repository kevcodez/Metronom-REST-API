package de.kevcodez.metronom.rest

import de.kevcodez.metronom.model.alert.Alert
import de.kevcodez.metronom.model.station.Station
import de.kevcodez.metronom.provider.AlertCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.stream.Stream
import kotlin.streams.toList

@RestController
@RequestMapping(value = ["alert"], produces = ["application/json"])
class AlertResource @Autowired constructor(
    private val alertCache: AlertCache,
    private val routeResource: RouteResource
) {

    @GetMapping
    fun findAllAlerts(): List<Alert> {
        return alertCache.getAlerts()
    }

    /**
     * Finds all alerts since the given date time. The date time must be in ISO-8601 format, otherwise an empty collection
     * will be returned.
     *
     * @param dateTime minimum date time
     * @return matching alerts
     */
    @GetMapping("since/{since}")
    fun findAlertsSince(@PathVariable(value = "since") dateTime: String): List<Alert> {
        val localDateTime = LocalDateTime.parse(dateTime)

        return streamAlerts().filter { d -> d.dateTime.isAfter(localDateTime) }.toList()
    }

    /**
     * Finds all alerts that contains the given text.
     *
     * @param text text to search for
     * @return matching alerts
     */
    @GetMapping("contains/{text}")
    fun findByText(@PathVariable(value = "text") text: String): List<Alert> {
        return streamAlerts().filter { d -> d.message.contains(text) }.toList()
    }

    /**
     * Finds the alerts relevant for the station with the given name that were created during the last hour.
     *
     * @param station station name
     * @return relevant alerts
     */
    @GetMapping("station/{station}")
    fun findRelevantAlertsForStation(@PathVariable(value = "station") station: String): List<Alert> {
        val routes = routeResource.findByStop(station)

        val stations = routes.stream().map { it.stations }
            .flatMap { it.stream() }
            .distinct()
            .toList()

        // Assume that only information that are max. 1 1/2 hour old are still relevant
        val oneAndAHalfHoursAgo = LocalTime.now().minusHours(1).minusMinutes(30)

        return findAllAlerts().stream()
            .filter { alert -> alert.plannedDeparture != null && alert.plannedDeparture.isAfter(oneAndAHalfHoursAgo) }
            .filter { alert -> isAlertRelevantForStation(stations, alert) }
            .toList()
    }

    private fun isAlertRelevantForStation(stations: List<Station>, alert: Alert): Boolean {
        return stations.contains(alert.stationStart) && stations.contains(alert.stationEnd)
    }

    /**
     * Finds the alerts where the system could not assign a start or stop station.
     *
     * @return list of alerts
     */
    @GetMapping("unassigned")
    fun findAlertsWithUnknownStation(): List<Alert> {
        return streamAlerts().toList()
    }

    private fun streamAlerts(): Stream<Alert> {
        return alertCache.getAlerts().stream()
    }

}
