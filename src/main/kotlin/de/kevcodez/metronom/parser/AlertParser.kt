package de.kevcodez.metronom.parser

import com.fasterxml.jackson.databind.ObjectMapper
import de.kevcodez.metronom.converter.AlertConverter
import de.kevcodez.metronom.model.alert.Alert
import de.kevcodez.metronom.utility.WebsiteSourceDownloader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * Class to parse alert notifications from the Metronom SOAP endpoint.
 *
 * @author Kevin Grüneberg
 */
@Component
class AlertParser @Autowired constructor(
    private val alertConverter: AlertConverter,
    private val websiteSourceDownloader: WebsiteSourceDownloader,
    private val objectMapper: ObjectMapper
) {

    /**
     * Parses the alerts from the Metronom SOAP endpoint.
     *
     * @return list of alerts
     */
    fun parseAlerts(): List<Alert> {
        val cookies = websiteSourceDownloader.getCookiesFromUrl("https://www.der-metronom.de/ueber-metronom/wer-wir-sind")
        val pageSource = websiteSourceDownloader.getSource(METRONOM_ALERT_URL, cookies)
        return parseAlertsFromSource(pageSource)
    }

    private fun parseAlertsFromSource(pageSource: String): List<Alert> {
        val mainJsonNode = objectMapper.readTree(pageSource)
        val alertJsonNode = mainJsonNode.get("stoerung")

        val alerts = ArrayList<Alert>()
        alertJsonNode.forEach { jsonAlert ->
            val alert = alertConverter.convert(jsonAlert)

            alerts.add(alert)
        }

        return alerts
    }

    companion object {

        const val METRONOM_ALERT_URL = "https://www.der-metronom.de/livedata/etc?type=troublelist&_=1538252265246"

    }

}
