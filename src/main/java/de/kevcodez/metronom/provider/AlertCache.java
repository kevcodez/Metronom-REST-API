package de.kevcodez.metronom.provider;

import de.kevcodez.metronom.model.alert.Alert;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AlertCache {

    private List<Alert> alerts = new ArrayList<>();

    public void addAlert(Alert alert) {
        if (!alerts.contains(alert)) {
            alerts.add(alert);
        }

        removeOldAlerts();
    }

    public List<Alert> getAlerts() {
        return Collections.unmodifiableList(alerts);
    }

    private void removeOldAlerts() {
        LocalDateTime fourHoursAgo = LocalDateTime.now().minusHours(4);
        alerts.removeIf(alert -> alert.getDateTime().isBefore(fourHoursAgo));
    }

}
