package de.kevcodez.metronom.rest

import de.kevcodez.metronom.model.delay.StationDelay
import de.kevcodez.metronom.parser.StationDelayParser
import de.kevcodez.metronom.provider.StationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST resource that provides possible delays for a given station.
 *
 * @author Kevin Grüneberg
 */
@RestController
@RequestMapping(value = ["stationDelay"], produces = ["application/json"])
class StationDelayResource @Autowired constructor(
    private val stationDelayParser: StationDelayParser
) {

    /**
     * Gets the delay information for the station with the given name.
     *
     * @param name station name
     * @return station delay information
     */
    @GetMapping("{name}")
    fun findStationDelayByName(@PathVariable("name") name: String): StationDelay? {
        // TODO Cache for at least 30 seconds
        return stationDelayParser.findDelays(name)
    }

    /**
     * Gets the delay information for the station with the given code.
     *
     * @param code station code
     * @return station delay information
     */
    @GetMapping("code/{code}")
    fun findStationDelayByCode(@PathVariable("code") code: String): StationDelay? {
        val station = StationProvider.findStationByCode(code)
        return findStationDelayByName(station.name)
    }
}
