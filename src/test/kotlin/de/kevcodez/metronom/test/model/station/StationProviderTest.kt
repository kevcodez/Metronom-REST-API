package de.kevcodez.metronom.test.model.station

import de.kevcodez.metronom.model.station.Station
import de.kevcodez.metronom.provider.StationProvider
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

class StationProviderTest {

    @Test
    fun shouldProvideStations() {
        val stations = StationProvider.getStations()
        assertThat(stations.size, greaterThan(0))
    }

    @Test
    fun shouldFindStationByCode() {
        val station = StationProvider.findStationByCode("AMA")

        assertThat(station, notNullValue())
        assertThat<String>(station.name, `is`("Maschen"))
        assertThat<String>(station.code, `is`("AMA"))
    }

    @Test
    fun shouldFindStationByName() {
        val station = StationProvider.findStationByName("Harburg")

        assertThat<Station>(station, notNullValue())
        assertThat<String>(station!!.name, `is`("Harburg"))
    }

    @Test
    fun shouldFindStationByAlternativeName() {
        val station = StationProvider.findStationByName("Hamburg-Harburg")

        assertThat<Station>(station, notNullValue())
        assertThat<String>(station!!.name, `is`("Harburg"))
    }

    @Test
    fun shouldHaveEqualStations() {
        val stationByCode = StationProvider.findStationByCode("AMA")
        val stationByName = StationProvider.findStationByName("Maschen")

        assertThat(stationByCode, `is`(stationByName))
        assertThat(stationByCode.hashCode(), `is`(stationByName!!.hashCode()))

        val differentStation = StationProvider.findStationByName("Harburg")
        assertThat(stationByCode, not(differentStation))
        assertThat(stationByCode.hashCode(), not(differentStation!!.hashCode()))
    }

}
