package de.kevcodez.metronom.parser

import com.fasterxml.jackson.databind.ObjectMapper
import de.kevcodez.metronom.converter.StationDelayConverter
import de.kevcodez.metronom.model.delay.StationDelay
import de.kevcodez.metronom.model.station.Station
import de.kevcodez.metronom.provider.StationProvider
import de.kevcodez.metronom.utility.WebsiteSourceDownloader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Parses the station delays from the Metronom station information endpoint.
 *
 * @author Kevin Grüneberg
 */
@Component
class StationDelayParser @Autowired constructor(
    private val stationDelayConverter: StationDelayConverter,
    private val websiteSourceDownloader: WebsiteSourceDownloader,
    private val objectMapper: ObjectMapper
) {

    fun findDelays(stationName: String): StationDelay? {
        val station = StationProvider.findStationByName(stationName)

        return findDelays(station)
    }

    /**
     * Finds the delay for the given station.<br></br>
     * To avoid getting an access denied exception when retrieving the station delays, we have to get the cookies from any
     * other URL first. We retrieve a CSRF token and a CraftSessionID that are used for authentication.
     *
     * @param station station to search for
     * @return station delay
     */
    private fun findDelays(station: Station?): StationDelay? {
        if (station == null) {
            return null
        }

        val uri = buildStationDelayUri(station)
        // Get cookies from any URL to avoid access denied exception
        val cookies = websiteSourceDownloader.getCookiesFromUrl("https://www.der-metronom.de/ueber-metronom/wer-wir-sind")
        val result = websiteSourceDownloader.getSource(uri, cookies)

        val jsonNode = objectMapper.readTree(result)

        return stationDelayConverter.convert(station, jsonNode)
    }

    private fun buildStationDelayUri(station: Station): String {
        return BASE_URL + "&bhf=" + station.code
    }

    companion object {

        private const val BASE_URL = "https://www.der-metronom.de/livedata/etc?type=stationsauskunft"

    }

}
