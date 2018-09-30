package de.kevcodez.metronom.converter

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeType
import de.kevcodez.metronom.model.delay.Departure
import de.kevcodez.metronom.model.delay.StationDelay
import de.kevcodez.metronom.model.station.Station
import de.kevcodez.metronom.provider.StationProvider
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.util.regex.Pattern

/**
 * Converts the JSON response from Metronom to a [StationDelay].
 *
 * @author Kevin Grüneberg
 */
@Component
class StationDelayConverter {

    fun convert(station: Station, node: JsonNode): StationDelay? {
        LOG.debug("converting json node {}", node)

        // the content is false if there is an error on the metronom endpoint
        if ("false" == node.toString()) {
            return null
        }

        val timeAsString = node.get("stand").asText()
        val time = LocalTime.parse(timeAsString)

        val stationDelay = StationDelay(station, time)

        val nodeDeparture = node.get("abfahrt") ?: return null

        if (nodeDeparture.nodeType == JsonNodeType.OBJECT) {
            // Parse single object
            val departure = parseDeparture(nodeDeparture)

            stationDelay.addDeparture(departure)
        } else {
            // Parse JSON array
            nodeDeparture.forEach {
                val departure = parseDeparture(it)

                stationDelay.addDeparture(departure)
            }
        }

        return stationDelay
    }

    private fun parseDeparture(singleDeparture: JsonNode): Departure {
        val time = singleDeparture.get("zeit").asText()
        val train = singleDeparture.get("zug").asText()
        val targetStationName = singleDeparture.get("ziel").asText()
        val delayInMinutes = singleDeparture.get("prognosemin").asInt()


        val prognose = singleDeparture.get("prognose").asText()
        val matcher = PATTERN_TRACK.matcher(prognose)

        val track = if (matcher.find()) {
            matcher.group(1)
        } else null

        val cancelled = prognose.toLowerCase().contains("fällt")

        val targetStation = StationProvider.findStationByName(targetStationName)!!

        return Departure(
            train = train,
            targetStation = targetStation,
            time = LocalTime.parse(time),
            delayInMinutes = delayInMinutes,
            isCancelled = cancelled,
            track = track
        )
    }

    companion object {

        private val LOG = LoggerFactory.getLogger(StationDelayConverter::class.java)

        private val PATTERN_TRACK = Pattern.compile("Gleis (\\d+)")
    }

}
