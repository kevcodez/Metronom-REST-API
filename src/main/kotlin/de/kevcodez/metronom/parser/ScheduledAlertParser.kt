package de.kevcodez.metronom.parser

import de.kevcodez.metronom.provider.AlertCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

/**
 * Class to parse alert notifications every minute (scheduled job).
 *
 * @author Kevin Grüneberg
 */
@Component
class ScheduledAlertParser @Autowired constructor(
    private val alertParser: AlertParser,
    private val alertCache: AlertCache
) {

    @PostConstruct
    fun onPostConstruct() {
        parseAlerts()
    }

    @Scheduled(fixedRate = (60 * 1000 * 30).toLong())
    fun parseAlerts() {
        alertParser.parseAlerts().forEach { alertCache.addAlert(it) }
    }

}
