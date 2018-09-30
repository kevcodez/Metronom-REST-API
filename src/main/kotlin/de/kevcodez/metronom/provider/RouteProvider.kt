package de.kevcodez.metronom.provider

import de.kevcodez.metronom.model.route.Route
import java.util.*

/**
 * The route provider provides all routes statically.
 *
 * @author Kevin Grüneberg
 */
object RouteProvider {

    private val routes = ArrayList<Route>()

    init {
        addRouteElbeTakt()
        addRouteNordseeTakt()
        addRouteWeserTakt()
        addRouteAllerTakt()
        addRouteLeinetalTakt()
    }

    fun getRoutes(): List<Route> {
        return Collections.unmodifiableList(routes)
    }

    private fun addRouteElbeTakt() {
        val route = Route("Elbe-Takt")
        route.addTrains("RB 31", "RE 3")

        val stations = arrayOf(
            "Hamburg",
            "Hamburg-Harburg",
            "Meckelfeld",
            "Maschen",
            "Stelle",
            "Ashausen",
            "Winsen",
            "Radbruch",
            "Bardowick",
            "Lüneburg",
            "Bienenbüttel",
            "Bad Bevensen",
            "Uelzen"
        )

        addStations(stations, route)
        routes.add(route)
    }

    private fun addRouteNordseeTakt() {
        val route = Route("Nordsee-Takt")
        route.addTrains("RE 5")

        val stations = arrayOf(
            "Hamburg",
            "Hamburg-Harburg",
            "Buxtehude",
            "Horneburg",
            "Stade",
            "Hammah",
            "Himmelpforten",
            "Hechthausen",
            "Hemmoor",
            "Wingst",
            "Cadenberge",
            "Cuxhaven"
        )

        addStations(stations, route)
        routes.add(route)
    }

    private fun addRouteWeserTakt() {
        val route = Route("Weser-Takt")
        route.addTrains("RB41", "RE 4")

        val stations = arrayOf(
            "Hamburg",
            "Hamburg-Harburg",
            "Hittfeld",
            "Klecken",
            "Buchholz",
            "Sprötze",
            "Tostedt",
            "Lauenbrück",
            "Scheeßel",
            "Rotenburg",
            "Sottrum",
            "Ottersberg",
            "Sagehorn",
            "Bremen-Oberneuland",
            "Bremen"
        )

        addStations(stations, route)
        routes.add(route)
    }

    private fun addRouteAllerTakt() {
        val route = Route("Aller-Takt")
        route.addTrains("RE 2")

        val stations = arrayOf(
            "Uelzen",
            "Suderburg",
            "Unterlüß",
            "Eschede",
            "Celle",
            "Großburgwedel",
            "Isernhagen",
            "Langenhagen Mitte",
            "Hannover"
        )

        addStations(stations, route)
        routes.add(route)
    }

    private fun addRouteLeinetalTakt() {
        val route = Route("Leinetal-Takt")
        route.addTrains("RE 2")

        val stations = arrayOf(
            "Hannover Hbf",
            "Sarstedt",
            "Nordstemmen",
            "Elze",
            "Banteln",
            "Alfeld",
            "Freden",
            "Kreiensen",
            "Einbeck Salzderhelden",
            "Northeim",
            "Nörten-Hardenberg",
            "Göttingen"
        )

        addStations(stations, route)
        routes.add(route)
    }

    private fun addStations(stops: Array<String>, route: Route) {
        Arrays.stream(stops)
            .map { StationProvider.findStationByName(it) }
            .forEach { route.addStation(it) }
    }

}
