package de.kevcodez.metronom.test.rest

import de.kevcodez.metronom.model.route.Route
import de.kevcodez.metronom.rest.RouteResource
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

class RouteResourceTest {

    private val routeResource: RouteResource = RouteResource()

    @Test
    fun shouldFindAllRoutes() {
        val routes = routeResource.findAllRoutes()
        assertThat(routes.size, greaterThan(0))
    }

    @Test
    fun shouldFindRouteByName() {
        val route = routeResource.findByName("Elbe-Takt")

        assertThat(route, notNullValue())
        assertThat<String>(route!!.name, `is`("Elbe-Takt"))
    }

    @Test
    fun shouldNotFindNonExistingRoute() {
        assertThat(routeResource.findByName("nope"), nullValue())
    }

    @Test
    fun shouldFindRouteByStop() {
        val route = routeResource.findByStop("Maschen")

        assertThat(route.size, `is`(1))

        assertThat(routeHasStation(route, "Maschen"), `is`(true))
    }

    @Test
    fun shouldFindRouteByStartAndStop() {
        val route = routeResource.findByStartAndStop("Maschen", "Meckelfeld")

        assertThat(route.size, `is`(1))

        assertThat(routeHasStation(route, "Maschen"), `is`(true))
        assertThat(routeHasStation(route, "Meckelfeld"), `is`(true))
    }

    private fun routeHasStation(route: List<Route>, stationName: String): Boolean {
        return route[0].stations.stream().map { it.name }
            .anyMatch { stationName == it }
    }

}
