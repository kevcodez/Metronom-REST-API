package de.kevcodez.metronom.test.model.alert;

import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.parser.AlertParser;
import de.kevcodez.metronom.parser.ScheduledAlertParser;
import de.kevcodez.metronom.provider.AlertCache;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests {@link ScheduledAlertParser}.
 * 
 * @author Kevin Grüneberg
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduledAlertParserTest {

  @InjectMocks
  private ScheduledAlertParser scheduledAlertParser;

  @Mock
  private AlertParser alertParser;

  @Mock
  private AlertCache alertCache;

  @Test
  public void shouldParseAlertsOnPostConstruct() {
    Mockito.when(alertParser.parseAlerts()).thenReturn(constructSampleAlerts());

    scheduledAlertParser.onPostConstruct();
    
    // Make sure parse alerts was called
    Mockito.verify(alertParser).parseAlerts();

    // Two object should be added to alert cache
    Mockito.verify(alertCache, Mockito.times(2)).addAlert(Mockito.any(Alert.class));
  }

  private List<Alert> constructSampleAlerts() {
    List<Alert> alerts = new ArrayList<>();
    alerts.add(new Alert("1", "msg", LocalDateTime.now(), null));
    alerts.add(new Alert("2", "msg", LocalDateTime.now(), null));
    
    return alerts;
  }

}
