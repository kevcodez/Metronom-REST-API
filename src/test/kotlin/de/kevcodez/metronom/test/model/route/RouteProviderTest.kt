package de.kevcodez.metronom.test.model.route

import de.kevcodez.metronom.provider.RouteProvider
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

class RouteProviderTest {

    @Test
    fun shouldProvideRoutes() {
        val routes = RouteProvider.getRoutes()

        assertThat(routes.size, greaterThan(0))
    }

    @Test
    fun shouldProvideEqualRoutes() {
        val routes = RouteProvider.getRoutes()

        val firstRoute = routes[0]
        val secondRoute = routes[1]

        assertThat(firstRoute, `is`(firstRoute))
        assertThat(firstRoute, not(secondRoute))
        assertThat(firstRoute.hashCode(), not(secondRoute.hashCode()))
    }

}
