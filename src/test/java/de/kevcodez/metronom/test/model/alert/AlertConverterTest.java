package de.kevcodez.metronom.test.model.alert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.kevcodez.metronom.converter.AlertConverter;
import de.kevcodez.metronom.model.alert.Alert;

import java.io.IOException;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link AlertConverter}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class AlertConverterTest {

  private static ObjectMapper objectMapper = new ObjectMapper();

  private AlertConverter alertConverter;

  @Before
  public void init() {
    alertConverter = new AlertConverter();
  }

  @Test
  public void shouldConvertAlert() throws IOException {
    String json = "{'@attributes':{'ID':'43445'},'Stoermeldung':'ME 82131 von Hamburg nach Uelzen ab Winsen ca. 10 Minuten','Empfangsdatum':'28.07.2016 20:33:20','Created':'28.07.2016 20:33:20'}";

    Alert alert = alertConverter.convert(objectMapper.readTree(json.replace("'", "\"")));
    assertThat(alert, is(notNullValue()));

    assertThat(alert.getId(), is("43445"));
    assertThat(alert.getMessage(), is("ME 82131 von Hamburg nach Uelzen ab Winsen ca. 10 Minuten"));
    assertThat(alert.getCreationDate().toString(), is("2016-07-28T20:33:20"));
  }

  @Test
  public void shouldParsePlannedDeparture() {
    LocalTime plannedDeparture = alertConverter.parsePlannedDeparture("(planmäßige Abfahrt 11:09 Uhr)");

    assertThat(plannedDeparture, is(LocalTime.of(11, 9)));
  }

  @Test
  public void shouldParsePlannedDeparture2() {
    LocalTime plannedDeparture = alertConverter.parsePlannedDeparture("(planmäßige Abfahrt 9:11 Uhr)");

    assertThat(plannedDeparture, is(LocalTime.of(9, 11)));
  }

}
