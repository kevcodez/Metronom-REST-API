package de.kevcodez.metronom.test.model.route

import de.kevcodez.metronom.provider.RouteProvider
import de.kevcodez.metronom.provider.StationProvider
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RouteProviderTest {

    private var routeProvider: RouteProvider? = null

    private val realStationProvider: StationProvider = StationProvider()

    @BeforeEach
    fun setup() {
        realStationProvider.constructStations()

        routeProvider = RouteProvider(realStationProvider)
        routeProvider!!.constructRoutes()
    }

    @Test
    fun shouldProvideRoutes() {
        val routes = routeProvider!!.getRoutes()

        assertThat(routes.size, greaterThan(0))
    }

    @Test
    fun shouldProvideEqualRoutes() {
        val routes = routeProvider!!.getRoutes()

        val firstRoute = routes[0]
        val secondRoute = routes[1]

        assertThat(firstRoute, `is`(firstRoute))
        assertThat(firstRoute, not(secondRoute))
        assertThat(firstRoute.hashCode(), not(secondRoute.hashCode()))
    }

}
