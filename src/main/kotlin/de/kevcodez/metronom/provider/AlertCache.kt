package de.kevcodez.metronom.provider

import de.kevcodez.metronom.model.alert.Alert
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class AlertCache {

    private val alerts = ArrayList<Alert>()

    fun addAlert(alert: Alert) {
        if (!alerts.contains(alert)) {
            alerts.add(alert)
        }

        removeOldAlerts()
    }

    fun getAlerts(): List<Alert> {
        return Collections.unmodifiableList(alerts)
    }

    private fun removeOldAlerts() {
        val fourHoursAgo = LocalDateTime.now().minusHours(4)
        alerts.removeIf { alert -> alert.dateTime.isBefore(fourHoursAgo) }
    }

}
