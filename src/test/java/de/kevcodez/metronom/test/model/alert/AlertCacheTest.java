package de.kevcodez.metronom.test.model.alert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.provider.AlertCache;

import java.time.LocalDateTime;

import javax.enterprise.event.Event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests {@link AlertCache}.
 * 
 * @author Kevin Grüneberg
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AlertCacheTest {

  @InjectMocks
  private AlertCache alertCache;

  @Mock
  private Event<Alert> event;

  @Test
  public void shouldAddAlert() {
    alertCache.addAlert(new Alert("1", "message", LocalDateTime.now(), null));
    assertThat(alertCache.getAlerts().size(), is(1));
  }

  @Test
  public void shouldRemoveOldAlerts() {
    LocalDateTime fiveHoursAgo = LocalDateTime.now().minusHours(5);

    alertCache.addAlert(new Alert("1", "message", LocalDateTime.now(), null));
    alertCache.addAlert(new Alert("2", "message", LocalDateTime.now(), null));
    assertThat(alertCache.getAlerts().size(), is(2));

    alertCache.addAlert(new Alert("2", "message", fiveHoursAgo, null));
    assertThat(alertCache.getAlerts().size(), is(2));
  }

}
