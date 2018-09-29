package de.kevcodez.metronom.parser;

import de.kevcodez.metronom.provider.AlertCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Class to parse alert notifications every minute (scheduled job).
 *
 * @author Kevin Grüneberg
 *
 */
@Component
public class ScheduledAlertParser {

    @Autowired
    private AlertParser alertParser;

    @Autowired
    private AlertCache alertCache;

    @PostConstruct
    public void onPostConstruct() {
        parseAlerts();
    }

    @Scheduled(fixedRate = 60 * 1000 * 30)
    public void parseAlerts() {
        alertParser.parseAlerts().forEach(alertCache::addAlert);
    }

}
