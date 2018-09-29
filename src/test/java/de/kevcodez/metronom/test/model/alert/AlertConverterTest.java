package de.kevcodez.metronom.test.model.alert;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kevcodez.metronom.converter.AlertConverter;
import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.provider.StationProvider;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AlertConverterTest {

  private static ObjectMapper objectMapper = new ObjectMapper();

  private AlertConverter alertConverter;

  private StationProvider stationProvider = new StationProvider();

  @Before
  public void init() {
    stationProvider.constructStations();
    alertConverter = new AlertConverter(stationProvider);
  }

  @Test
  public void shouldConvertAlert() throws IOException {
    //language=JSON
    String json = "{\n" +
            "  \"netz\": \"Hamburg-Uelzen\",\n" +
            "  \"bhfvon\": \"Stelle\",\n" +
            "  \"bhfnach\": \"Maschen\",\n" +
            "  \"text\": \"81638 L\\u00fcneburg \\u2192 Hamburg Hbf hat in Maschen (planm\\u00e4\\u00dfig 22:00) +14 min. Grund: Einwirkung Dritter \\/ Eingriff in den Bahnverkehr.\",\n" +
            "  \"datum\": \"2018-09-29+02:00\",\n" +
            "  \"time_von\": \"22:00:48.000+01:00\",\n" +
            "  \"time_bis\": \"00:20:42.000+01:00\"\n" +
            "}";

    Alert alert = alertConverter.convert(objectMapper.readTree(json));
    assertThat(alert, is(notNullValue()));

    assertThat(alert.getMessage(), is("81638 Lüneburg → Hamburg Hbf hat in Maschen (planmäßig 22:00) +14 min. Grund: Einwirkung Dritter / Eingriff in den Bahnverkehr."));
    assertThat(alert.getCreationDate().toString(), is("2018-09-29"));
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
