package de.kevcodez.metronom.rest

import de.kevcodez.metronom.model.route.Route
import de.kevcodez.metronom.provider.RouteProvider
import de.kevcodez.metronom.provider.StationProvider.Companion.alternativeNameMatches
import de.kevcodez.metronom.provider.StationProvider.Companion.stationNameMatches
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST resource that provides Metronom routes.
 *
 * @author Kevin Grüneberg
 */
@RestController
@RequestMapping(value = ["route"], produces = ["application/json"])
class RouteResource @Autowired constructor(
    private val routeProvider: RouteProvider
) {

    @GetMapping
    fun findAllRoutes(): List<Route> {
        return routeProvider.getRoutes()
    }

    @GetMapping("stop/{stop}")
    fun findByStop(@PathVariable(value = "stop") stop: String?): List<Route> {
        return if (stop == null) {
            emptyList()
        } else routeProvider.getRoutes().filter { hasStation(it, stop) }
    }

    private fun hasStation(route: Route, stationName: String): Boolean {
        return route.stations
            .any { station -> stationNameMatches(stationName, station) || alternativeNameMatches(stationName, station) }
    }

    @GetMapping("name/{name}")
    fun findByName(@PathVariable(value = "name") name: String): Route? {
        return routeProvider.getRoutes().firstOrNull { name.equals(it.name, ignoreCase = true) }
    }

    @GetMapping("{start}/to/{stop}")
    fun findByStartAndStop(
        @PathVariable(value = "start") start: String,
        @PathVariable(value = "stop") stop: String
    ): List<Route> {
        return routeProvider.getRoutes().filter { hasStation(it, start) && hasStation(it, stop) }
    }

}
