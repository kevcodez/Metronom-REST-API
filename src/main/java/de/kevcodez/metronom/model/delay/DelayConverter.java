package de.kevcodez.metronom.model.delay;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converts the JSON object from the Metronom endpoint to the internal {@link Delay} object.
 * 
 * @author Kevin Grüneberg
 *
 */
public class DelayConverter {

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

  /**
   * Converts the given JSON object to a {@link Delay}.
   * 
   * @param delay delay as JSON OBJECT
   * @return converted delay
   */
  public static Delay convert(JsonNode delay) {
    String meldung = delay.get("Stoermeldung").textValue();
    String empfangsdatum = delay.get("Empfangsdatum").textValue();
    String id = delay.get("@attributes").get("ID").textValue();
    LocalDateTime dateTime = LocalDateTime.parse(empfangsdatum, formatter);

    return new Delay(id, meldung, dateTime);
  }

}
