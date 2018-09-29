package de.kevcodez.metronom.test.model.delay

import com.fasterxml.jackson.databind.ObjectMapper
import de.kevcodez.metronom.converter.StationDelayConverter
import de.kevcodez.metronom.model.delay.StationDelay
import de.kevcodez.metronom.provider.StationProvider
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class StationDelayConverterTest {

    private val stationProvider = StationProvider()

    private val stationDelayConverter = StationDelayConverter(stationProvider)

    @BeforeEach
    fun init() {
        stationProvider.constructStations()
    }

    @Test
    fun shouldConvertJsonDepartures() {
        val json =
            "{'name':'Cuxhaven','stand':'22:44','abfahrt':[{'zeit':'23:15','zug':'81942','ziel':'Hamburg','prognose':'p\u00fcnktlich','prognosemin':'0'},{'zeit':'00:15','zug':'81944','ziel':'Rotenburg','prognose':'p\u00fcnktlich','prognosemin':'5'}]}"
                .replace("'".toRegex(), "\"")

        val station = stationProvider.findStationByName("Cuxhaven")
        val stationDelay = stationDelayConverter.convert(station!!, objectMapper.readTree(json))

        assertThat<StationDelay>(stationDelay, `is`(notNullValue()))
        assertThat(stationDelay!!.departures.size, `is`(2))

        val firstDeparture = stationDelay.departures[0]
        assertThat(firstDeparture.time.toString(), `is`("23:15"))
        assertThat<String>(firstDeparture.train, `is`("81942"))
        assertThat<String>(firstDeparture.targetStation.name, `is`("Hamburg"))
        assertThat(firstDeparture.delayInMinutes, `is`(0))

        val secondDeparture = stationDelay.departures[1]
        assertThat(secondDeparture.time.toString(), `is`("00:15"))
        assertThat<String>(secondDeparture.train, `is`("81944"))
        assertThat<String>(secondDeparture.targetStation.name, `is`("Rotenburg"))
        assertThat(secondDeparture.delayInMinutes, `is`(5))
    }

    @Test
    @Throws(IOException::class)
    fun shouldParseTrack() {
        val json =
            "{'name':'Cuxhaven','stand':'22:10','abfahrt':[{'zeit':'22:33','zug':'82835','ziel':'Hamburg','prognose':'p\u00fcnktlich, heute Gleis 8','prognosemin':'0','gleiswechsel':'8B'}]}"
                .replace("'".toRegex(), "\"")

        val station = stationProvider.findStationByName("Cuxhaven")
        val stationDelay = stationDelayConverter.convert(station!!, objectMapper.readTree(json))

        assertThat<StationDelay>(stationDelay, `is`(notNullValue()))
        assertThat(stationDelay!!.departures.size, `is`(1))

        assertThat<String>(stationDelay.departures[0].track, `is`("8"))
    }

    companion object {

        private val objectMapper = ObjectMapper()
    }

}
