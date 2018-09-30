package de.kevcodez.metronom.test.model.alert

import com.fasterxml.jackson.databind.ObjectMapper
import de.kevcodez.metronom.converter.AlertConverter
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalTime

class AlertConverterTest {

    private var alertConverter: AlertConverter? = null

    @BeforeEach
    fun init() {
        alertConverter = AlertConverter()
    }

    @Test
    fun shouldConvertAlert() {
        val json = """{
                "netz": "Hamburg-Uelzen",
                "bhfvon": "Stelle",
                "bhfnach": "Maschen",
                "text": "81638 Lüneburg → Hamburg Hbf hat in Maschen (planmäßig 22:00) +14 min. Grund: Einwirkung Dritter / Eingriff in den Bahnverkehr.",
                "datum": "2018-09-29+02:00",
                "time_von": "22:00:48.000+01:00",
                "time_bis": "00:20:42.000+01:00"
                }"""

        val alert = alertConverter!!.convert(objectMapper.readTree(json))
        assertThat(alert, `is`(notNullValue()))

        assertThat<String>(
            alert.message,
            `is`("81638 Lüneburg → Hamburg Hbf hat in Maschen (planmäßig 22:00) +14 min. Grund: Einwirkung Dritter / Eingriff in den Bahnverkehr.")
        )
        assertThat(alert.creationDate.toString(), `is`("2018-09-29"))
    }

    @Test
    fun shouldParsePlannedDeparture() {
        val plannedDeparture = alertConverter!!.parsePlannedDeparture("(planmäßige Abfahrt 11:09 Uhr)")

        assertThat<LocalTime>(plannedDeparture, `is`(LocalTime.of(11, 9)))
    }

    @Test
    fun shouldParsePlannedDeparture2() {
        val plannedDeparture = alertConverter!!.parsePlannedDeparture("(planmäßige Abfahrt 9:11 Uhr)")

        assertThat<LocalTime>(plannedDeparture, `is`(LocalTime.of(9, 11)))
    }

    companion object {

        private val objectMapper = ObjectMapper()
    }

}
