package de.kevcodez.metronom.converter

import com.fasterxml.jackson.databind.JsonNode
import de.kevcodez.metronom.model.alert.Alert
import de.kevcodez.metronom.provider.StationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.util.regex.Pattern

/**
 * Converts the JSON object from the Metronom endpoint to the internal [Alert] object.
 *
 * @author Kevin Grüneberg
 */
@Component
class AlertConverter @Autowired constructor(private val stationProvider: StationProvider) {

    fun convert(alert: JsonNode): Alert {
        val text = alert.get("text").textValue()
        val date = alert.get("datum").textValue()
        val timeFrom = alert.get("time_von").textValue()
        val timeTo = alert.get("time_von").textValue()

        return Alert(
            message = text,
            creationDate = LocalDate.parse(date!!.split("\\+".toRegex())[0]),
            plannedDeparture = parsePlannedDeparture(text),
            timeFrom = LocalTime.parse(timeFrom.split("\\+".toRegex())[0]),
            timeTo = LocalTime.parse(timeTo.split("\\+".toRegex())[0]),
            stationStart = stationProvider.findStationByName(alert.get("bhfvon").textValue())!!,
            stationEnd = stationProvider.findStationByName(alert.get("bhfnach").textValue())!!
        )
    }

    fun parsePlannedDeparture(alert: String): LocalTime? {
        val matcher = PATTERN_PLANNED_DEPARTURE.matcher(alert)

        if (matcher.find()) {
            var time = matcher.group("time")

            val splitTime = time.split(":")

            // Add leading zero
            if (splitTime[0].length == 1) {
                time = "0$time"
            }

            return LocalTime.parse(time)
        }

        return null
    }

    companion object {

        private val PATTERN_PLANNED_DEPARTURE = Pattern.compile("((?<time>\\d{1,2}:\\d{2})( ?)(Uhr)?)")

    }

}
