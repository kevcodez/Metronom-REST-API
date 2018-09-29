package de.kevcodez.metronom.test.model.station

import de.kevcodez.metronom.model.station.Station
import de.kevcodez.metronom.provider.StationProvider
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StationProviderTest {

    private var stationProvider: StationProvider = StationProvider()

    @BeforeEach
    fun before() {
        stationProvider.constructStations()
    }

    @Test
    fun shouldProvideStations() {
        val stations = stationProvider.getStations()
        assertThat(stations.size, greaterThan(0))
    }

    @Test
    fun shouldFindStationByCode() {
        val station = stationProvider.findStationByCode("AMA")

        assertThat(station, notNullValue())
        assertThat<String>(station.name, `is`("Maschen"))
        assertThat<String>(station.code, `is`("AMA"))
    }

    @Test
    fun shouldFindStationByName() {
        val station = stationProvider.findStationByName("Harburg")

        assertThat<Station>(station, notNullValue())
        assertThat<String>(station!!.name, `is`("Harburg"))
    }

    @Test
    fun shouldFindStationByAlternativeName() {
        val station = stationProvider.findStationByName("Hamburg-Harburg")

        assertThat<Station>(station, notNullValue())
        assertThat<String>(station!!.name, `is`("Harburg"))
    }

    @Test
    fun shouldHaveEqualStations() {
        val stationByCode = stationProvider.findStationByCode("AMA")
        val stationByName = stationProvider.findStationByName("Maschen")

        assertThat(stationByCode, `is`<Station>(stationByName))
        assertThat(stationByCode.hashCode(), `is`(stationByName!!.hashCode()))

        val differentStation = stationProvider.findStationByName("Harburg")
        assertThat(stationByCode, not<Station>(differentStation))
        assertThat(stationByCode.hashCode(), not(differentStation!!.hashCode()))
    }

}
