package de.kevcodez.metronom.rest

import de.kevcodez.metronom.model.station.Station
import de.kevcodez.metronom.provider.StationProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST resource that provides stations.
 *
 * @author Kevin Grüneberg
 */
@RequestMapping(value = ["station"], produces = ["application/json"])
@RestController
class StationResource {

    @GetMapping
    fun findAll(): List<Station> {
        return StationProvider.getStations()
    }

    @GetMapping("name/{name}")
    fun findByName(@PathVariable(value = "name") name: String): Station? {
        return StationProvider.findStationByName(name)
    }

    @GetMapping("code/{code}")
    fun findByCode(@PathVariable(value = "code") code: String): Station {
        return StationProvider.findStationByCode(code)
    }

}
